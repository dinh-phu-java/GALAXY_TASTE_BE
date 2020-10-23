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

    ResponseEntity<?> deleteProduct(Long id) throws ProductNotFoundException;
}
