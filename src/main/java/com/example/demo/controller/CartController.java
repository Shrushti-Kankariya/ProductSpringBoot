package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add product to cart
    @PostMapping("/add/{productId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(@PathVariable Long productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            cartService.addToCart(productId);
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to add product to cart.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Get cart items (JSON)
    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<List<Cart>> getCartItems() {
        return ResponseEntity.ok(cartService.getCartItems());
    }

    // Display cart page (Thymeleaf)
    @GetMapping
    public String showCart(Model model) {
        List<Cart> cartItems = cartService.getCartItems();
        double cartTotal = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);

        return "cart"; // Renders cart.html
    }

    // Checkout: Clear cart
    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<Map<String, String>> checkout() {
        cartService.clearCart();
        return ResponseEntity.ok(Map.of("message", "Checkout successful!"));
    }
}
