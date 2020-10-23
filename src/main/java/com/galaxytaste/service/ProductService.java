package com.galaxytaste.service;

import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    Page<Product> findAllProduct(Pageable pageable);
    Product getProductById(Long id) throws ProductNotFoundException;
    Product save(Product product);
    Product updateProduct(Long id,Product newProduct) throws ProductNotFoundException;
    ResponseEntity<?> deleteProduct(Long id) throws ProductNotFoundException;
}
