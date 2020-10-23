package com.galaxytaste.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private Long id;
    private String productName;
    private double productPrice;
    private String tag;
    private String description;
    private String[] productImageUrl;
    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    public Product() {
    }

    public Product(Long id, String productName, double productPrice, String tag, String description, String[] productImageUrl) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.tag = tag;
        this.description = description;
        this.productImageUrl = productImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String[] productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
