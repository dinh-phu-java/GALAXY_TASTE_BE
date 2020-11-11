package com.galaxytaste.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.UUID;

@Entity
@Table(name = "product")
@JsonIgnoreProperties
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String productCode;
    private String productName;
    private double productPrice;
    private String tag;
    private String description;
    @Column(columnDefinition = "LONGBLOB")
    private String[] productImageUrl;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
        this.productCode= UUID.randomUUID().toString();
    }

    public Product(Long id, String productName, double productPrice, String tag, String description, String[] productImageUrl, Category category) {
        this.productCode= UUID.randomUUID().toString();
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.tag = tag;
        this.description = description;
        this.productImageUrl = productImageUrl;
        this.category = category;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
