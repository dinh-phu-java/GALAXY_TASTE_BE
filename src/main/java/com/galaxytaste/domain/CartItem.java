package com.galaxytaste.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="cart_item")
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,cascade={DETACH,MERGE,PERSIST,REFRESH})
    @JoinColumn(name="cart_id")
    @JsonIgnore
    private Cart cart;
    private Long productId;
    @Column(columnDefinition="LONGTEXT")
    private String productImage;
    private double price;
    private int amount;
    private String productName;

    public CartItem() {
    }


    public CartItem(Long id, Long productId, String productImage, double price, int amount,String productName) {
        this.id = id;
        this.productId = productId;
        this.productImage = productImage;
        this.price = price;
        this.amount = amount;
        this.productName=productName;
    }

    public CartItem(Long productId, String productImage, double price, int amount,String productName) {
        this.productId = productId;
        this.productImage = productImage;
        this.price = price;
        this.amount = amount;
        this.productName=productName;
    }


    public CartItem(Long id, Cart cart, Long productId, String productImage, double price, int amount,String productName) {
        this.id = id;
        this.cart = cart;
        this.productId = productId;
        this.productImage = productImage;
        this.price = price;
        this.amount = amount;
        this.productName=productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }



    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", productImage='" + productImage + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
