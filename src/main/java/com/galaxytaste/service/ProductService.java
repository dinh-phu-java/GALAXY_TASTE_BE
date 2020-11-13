package com.galaxytaste.service;

import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    Product getProductByProductCode(String productCode);
    Product getProductById(Long id) throws ProductNotFoundException;
    Product save(Product product);
    Product updateProduct(Long id,Product newProduct) throws ProductNotFoundException;
    ResponseEntity<?> deleteProduct(Long id) throws ProductNotFoundException;
}
