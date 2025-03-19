package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Cart;
import com.example.demo.service.CartService;


@Controller
public class CartUIController {
    @Autowired
    private CartService cartService;

    @GetMapping("/cart-ui")
    public String showCart(Model model) {
        List<Cart> cartItems = cartService.getCartItems();
        double cartTotal = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        return "cart";
    }
}
