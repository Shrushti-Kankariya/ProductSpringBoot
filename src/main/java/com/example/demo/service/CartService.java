package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId) {
        logger.info("Adding product with ID: {}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cartItem = new Cart(product, 1);
        cartRepository.save(cartItem);

        logger.info("Cart item saved: {}", cartItem);
    }

    public List<Cart> getCartItems() {
    	 return cartRepository.findAllWithProducts();
    }

    public void clearCart() {
        cartRepository.deleteAll();
    }
}
