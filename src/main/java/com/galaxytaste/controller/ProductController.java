package com.galaxytaste.controller;

import com.galaxytaste.domain.Category;
import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import com.galaxytaste.repository.CategoryRepository;
import com.galaxytaste.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/product/list")
    public Page<Product> getAllProducts(Pageable pageable){
        return this.productService.findAllProduct(pageable);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Category category = this.categoryRepository.findById(1L).get();
        product.setCategory(category);
        Product newProduct = this.productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) throws ProductNotFoundException {
        Product product = this.productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product updateProduct) throws ProductNotFoundException {
        Product newProduct=this.productService.updateProduct(id,updateProduct);
        return new ResponseEntity<>(newProduct,HttpStatus.OK);
    }
}
