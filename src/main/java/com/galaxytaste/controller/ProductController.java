package com.galaxytaste.controller;

import com.galaxytaste.domain.Category;
import com.galaxytaste.domain.HttpResponse;
import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductBadRequest;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import com.galaxytaste.model.ProductRequestModel;
import com.galaxytaste.repository.CategoryRepository;
import com.galaxytaste.service.ProductService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/product/list")
    public Page<Product> getAllProducts(Pageable pageable){
        return this.productService.findAllProduct(pageable);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestModel product, BindingResult theBinding) throws ProductBadRequest {
        if(theBinding.hasErrors()){
            String errorName=theBinding.getAllErrors().get(0).getDefaultMessage();
            LOGGER.warn(errorName);
            throw new ProductBadRequest(errorName);
        }
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Category category = this.categoryRepository.findById(product.getCategoryId()).get();
        Product productDto= modelMapper.map(product,Product.class);
        productDto.setCategory(category);
        Product newProduct = this.productService.save(productDto);
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
