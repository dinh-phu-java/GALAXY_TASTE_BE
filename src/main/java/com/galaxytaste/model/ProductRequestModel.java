package com.galaxytaste.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductRequestModel {

    @NotNull(message="Product Name must not be null")
    private String productName;
    @NotNull(message="Product Price must not be null")
    private double productPrice;
    @NotNull(message="Product Tag must not be null")
    private String tag;
    @NotNull(message="Product Description must not be null")
    private String description;
    @NotNull(message="Product Image must not be null")
    private String[] productImageUrl;
    @NotNull(message="Product Category ID must not be null")
    private Long categoryId;
    @NotNull(message="Image Files Can't be null")
    private MultipartFile[] imageFiles;

    public ProductRequestModel() {
    }

    public ProductRequestModel(@NotNull String productName, @NotNull double productPrice, @NotNull String tag, @NotNull String description, @NotNull String[] productImageUrl, @NotNull Long categoryId) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.tag = tag;
        this.description = description;
        this.productImageUrl = productImageUrl;
        this.categoryId = categoryId;
    }

    public ProductRequestModel(@NotNull(message = "Product Name must not be null") String productName, @NotNull(message = "Product Price must not be null") double productPrice, @NotNull(message = "Product Tag must not be null") String tag, @NotNull(message = "Product Description must not be null") String description, @NotNull(message = "Product Image must not be null") String[] productImageUrl, @NotNull(message = "Product Category ID must not be null") Long categoryId, @NotNull(message = "Image Files Can't be null") MultipartFile[] imageFiles) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.tag = tag;
        this.description = description;
        this.productImageUrl = productImageUrl;
        this.categoryId = categoryId;
        this.imageFiles = imageFiles;
    }

    public MultipartFile[] getImageFiles() {
        return imageFiles;
    }

    public void setImageFiles(MultipartFile[] imageFiles) {
        this.imageFiles = imageFiles;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
