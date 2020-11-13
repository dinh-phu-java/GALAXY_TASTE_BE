package com.galaxytaste.service.impl;

import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import com.galaxytaste.repository.ProductRepository;
import com.galaxytaste.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Product not found!";
    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> findAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        return this.productRepository.findById(id)
        .orElseThrow(()-> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product newProduct) throws ProductNotFoundException {
        Product currentProduct=getProductById(id);
        currentProduct.setCategory(newProduct.getCategory());
        currentProduct.setDescription(newProduct.getDescription());
        currentProduct.setProductImageUrl(newProduct.getProductImageUrl());
        currentProduct.setProductName(newProduct.getProductName());
        currentProduct.setProductPrice(newProduct.getProductPrice());
        currentProduct.setTag(newProduct.getTag());
        save(currentProduct);
        return currentProduct;
    }


    @Override
    public ResponseEntity<?> deleteProduct(Long id) throws ProductNotFoundException {
//        Product deleteProduct=getProductById(id).get();
//        if(deleteProduct==null){
//            throw new ProductNotFoundException(PRODUCT_NOT_FOUND);
//        }
//
//        this.productRepository.deleteById(id);
//        return deleteProduct;
        return this.productRepository.findById(id).map(product->{
            this.productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElseThrow(()-> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }
}
