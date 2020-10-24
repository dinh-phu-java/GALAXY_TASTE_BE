package com.galaxytaste.service;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import com.galaxytaste.exception.domain.UserNotFoundException;

public interface CartItemService {
    CartItem createCartItem(String username, Long productId, String productImage, double price, int amount) throws UserNotFoundException;
    CartItem updateAmountCartItem(int amount);
    CartItem deleteCartItem(Long id,String username);
}
