package com.galaxytaste.controller;

import com.galaxytaste.domain.Category;
import com.galaxytaste.domain.Product;
import com.galaxytaste.repository.CategoryRepository;
import com.galaxytaste.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;


    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product)  {
        Category category=this.categoryRepository.findById(1L).get();
        product.setCategory(category);
        Product newProduct=this.productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }


}
