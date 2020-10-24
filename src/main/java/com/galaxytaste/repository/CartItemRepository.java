package com.galaxytaste.repository;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCart(Cart cart);
}
