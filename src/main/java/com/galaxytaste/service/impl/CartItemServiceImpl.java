package com.galaxytaste.service.impl;

import com.galaxytaste.domain.Cart;
import com.galaxytaste.domain.CartItem;
import com.galaxytaste.domain.User;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.repository.CartItemRepository;
import com.galaxytaste.repository.UserRepository;
import com.galaxytaste.service.CartItemService;
import com.galaxytaste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User user= this.userRepository.findUserByUsername(username);
        if(user==null){
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME);
        }
        Cart cart= user.getCart();
        CartItem item=new CartItem();
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
    public CartItem updateAmountCartItem(int amount) {
        return null;
    }

    @Override
    public CartItem deleteCartItem(Long id, String username) {
        return null;
    }
}
