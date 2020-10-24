package com.galaxytaste.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="cart")
public class Cart implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;
    @OneToOne(fetch = FetchType.LAZY,mappedBy="cart",cascade={DETACH,MERGE,PERSIST,REFRESH})
    private User user;
    @OneToMany(fetch=FetchType.LAZY,mappedBy="cart",cascade = {ALL})
    private List<CartItem> cartItems;

    public CartItem addToCart(CartItem cartItem){
        if(this.cartItems == null){
            this.cartItems=new ArrayList<>();
        }
        this.cartItems.add(cartItem);
        return cartItem;
    }

    public Cart() {
    }

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
