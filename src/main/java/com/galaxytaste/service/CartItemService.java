package com.galaxytaste.service;

import com.galaxytaste.domain.CartItem;
import com.galaxytaste.exception.domain.UserNotFoundException;

public interface CartItemService {
    CartItem createCartItem(String username, Long productId, String productImage, double price, int amount,String productName) throws UserNotFoundException;

    CartItem updateAmountCartItem(String username, int cartItemIndex, int amount) throws UserNotFoundException;

    CartItem deleteCartItem(String username, int cartItemIndex) throws UserNotFoundException;

    void deleteAllCartItemByUsername(String username) throws UserNotFoundException;
}
