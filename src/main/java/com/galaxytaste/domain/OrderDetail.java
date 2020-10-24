package com.galaxytaste.domain;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;


@Entity
@Table(name="order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(cascade={DETACH,MERGE,PERSIST,REFRESH})
    @JoinColumn(name="order_id")
    private Order order;
    private Long productId;
    @Column(columnDefinition="LONGTEXT")
    private String productImage;
    private double price;
    private int amount;
    private String productName;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Long productId, String productImage, double price, int amount, String productName) {
        this.order = order;
        this.productId = productId;
        this.productImage = productImage;
        this.price = price;
        this.amount = amount;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
