package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByProductId(Long productId);
    
    @Query("SELECT c FROM Cart c JOIN FETCH c.product")
    List<Cart> findAllWithProducts();
}
