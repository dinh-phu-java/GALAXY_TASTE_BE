package com.galaxytaste.service.impl;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import com.galaxytaste.domain.User;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.repository.CartItemRepository;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.galaxytaste.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartItem createCartItem(String username, Long productId, String productImage, double price, int amount) throws UserNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME);
        }
        Cart cart = user.getCart();
        CartItem item = new CartItem();
        item.setAmount(amount);
        item.setPrice(price);
        item.setProductImage(productImage);
        item.setProductId(productId);
        cart.addToCart(item);
        item.setCart(cart);
        this.userRepository.save(user);
//        this.cartItemRepository.save(item);
        return item;
    }

    @Override
    public CartItem updateAmountCartItem(String username, int cartItemIndex, int amount) throws UserNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME);
        }
        Cart cart = user.getCart();
        List<CartItem> listCartItem = cart.getCartItems();
        CartItem currentCartItem = listCartItem.get(cartItemIndex);
        currentCartItem.setAmount(amount);
        cart.getCartItems().set(cartItemIndex, currentCartItem);
        this.userRepository.save(user);
        return currentCartItem;
    }

    //Need token
    @Override
    public CartItem deleteCartItem(String username, int cartItemIndex) throws UserNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME);
        }
        Cart cart = user.getCart();
        CartItem deleteCartItem=cart.getCartItems().remove(cartItemIndex);
        this.cartItemRepository.delete(deleteCartItem);
        this.userRepository.save(user);
        return deleteCartItem;
    }
}
