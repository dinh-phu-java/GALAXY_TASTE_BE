package com.galaxytaste.controller;

import com.galaxytaste.domain.CartItem;
import com.galaxytaste.exception.domain.UserNotFoundException;
import com.galaxytaste.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping("/cart-item")
    public ResponseEntity<CartItem> addToCart(@RequestParam String username, @RequestParam Long productId, @RequestParam String productImage, @RequestParam double price, @RequestParam int amount) throws UserNotFoundException {
        CartItem item = this.cartItemService.createCartItem(username, productId, productImage, price, amount);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/cart-item")
    public ResponseEntity<CartItem> updateAmount(@RequestParam String username,@RequestParam int cartItemIndex,@RequestParam int amount) throws UserNotFoundException {
       CartItem item= this.cartItemService.updateAmountCartItem(username,cartItemIndex,amount);
        return new ResponseEntity<>(item,HttpStatus.OK);
    }

    // need to user token
    @DeleteMapping("/cart-item")
    public ResponseEntity<CartItem> deleteCartItem(@RequestParam String username,@RequestParam int cartItemIndex) throws UserNotFoundException {
       CartItem deleteItem= this.cartItemService.deleteCartItem(username,cartItemIndex);
       return new ResponseEntity<>(deleteItem,HttpStatus.OK);
    }

//    @GetMapping("/cart-item")
    
}
