package com.galaxytaste.controller;

import com.galaxytaste.domain.Category;
import com.galaxytaste.domain.HttpResponse;
import com.galaxytaste.domain.Product;
import com.galaxytaste.exception.domain.ProductBadRequest;
import com.galaxytaste.exception.domain.ProductNotFoundException;
import com.galaxytaste.model.ProductRequestModel;
import com.galaxytaste.repository.CategoryRepository;
import com.galaxytaste.service.ProductService;
import com.galaxytaste.service.S3Services;
import com.galaxytaste.utilities.HashUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;


    @Autowired
    private S3Services s3Services;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/product/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = this.productService.findAllProduct();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestParam String productName,
                                                 @RequestParam String productPrice,
                                                 @RequestParam String tag,
                                                 @RequestParam String description,
                                                 @RequestParam String[] productImageUrl,
                                                 @RequestParam String categoryId,
                                                 @RequestParam MultipartFile[] imageFiles
    ) throws IOException {

        ArrayList<String> cloudImageUrl = new ArrayList<>();
        MultipartFile[] files = imageFiles;
        for (int i = 0; i < files.length; i++) {
            cloudImageUrl.add(this.s3Services.uploadService(files[i]));
        }

        String databaseImageUrl[] = new String[cloudImageUrl.size()];

        for (int j = 0; j < cloudImageUrl.size(); j++) {
            databaseImageUrl[j] = cloudImageUrl.get(j);
        }

        Category category = this.categoryRepository.findById(Long.parseLong(categoryId)).get();
        Product productDto = new Product(productName, Double.parseDouble(productPrice), tag, description, databaseImageUrl, category);
        productDto.setCategory(category);
        Product newProduct = this.productService.save(productDto);
        return new ResponseEntity<>(newProduct, HttpStatus.OK);
    }

    @PutMapping("/product/{productCode}")
    public ResponseEntity<Product> updateProduct(@RequestParam String productName,
                                                 @RequestParam String productPrice,
                                                 @RequestParam String tag,
                                                 @RequestParam String description,
                                                 @RequestParam String[] productImageUrl,
                                                 @RequestParam String categoryId,
                                                 @RequestParam MultipartFile[] imageFiles,
                                                 @PathVariable String productCode
    ) throws IOException {

        Product currentProduct = this.productService.getProductByProductCode(productCode);

        if (productImageUrl.length != 0 && imageFiles.length != 0) {
            //delete image in aws s3
            for(String awsUrl : currentProduct.getProductImageUrl()){
                String deleteImgName= awsUrl.substring(awsUrl.lastIndexOf('/')+1);
                this.s3Services.deleteFile(deleteImgName);
            }
//            upload image to aws s3
            ArrayList<String> cloudImageUrl = new ArrayList<>();
            MultipartFile[] files = imageFiles;
            for (int i = 0; i < files.length; i++) {
                String hashImageName=this.s3Services.uploadService(files[i]);
                cloudImageUrl.add(hashImageName);
            }

            String databaseImageUrl[] = new String[cloudImageUrl.size()];

            for (int j = 0; j < cloudImageUrl.size(); j++) {
                databaseImageUrl[j] = cloudImageUrl.get(j);
            }
            currentProduct.setProductImageUrl(databaseImageUrl);


        }
        Category category = this.categoryRepository.findById(Long.parseLong(categoryId)).get();
        currentProduct.setCategory(category);
        currentProduct.setProductName(productName);
        currentProduct.setProductPrice(Long.parseLong(productPrice));
        currentProduct.setTag(tag);
        currentProduct.setDescription(description);
        Product returnValue= this.productService.save(currentProduct);

        return new ResponseEntity<>(returnValue, HttpStatus.OK);
    }

//    @PostMapping("/product")
//    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequestModel product, BindingResult theBinding) throws ProductBadRequest, IOException {
//        if(theBinding.hasErrors()){
//            String errorName=theBinding.getAllErrors().get(0).getDefaultMessage();
//            LOGGER.warn(errorName);
//            throw new ProductBadRequest(errorName);
//        }
//
//        MultipartFile[] files = product.getImageFiles();
//        for(int i=0;i<files.length;i++){
//            this.s3Services.uploadService(files[i]);
//        }
//
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        Category category = this.categoryRepository.findById(product.getCategoryId()).get();
//        Product productDto= modelMapper.map(product,Product.class);
//        productDto.setCategory(category);
//        Product newProduct = this.productService.save(productDto);
//        return new ResponseEntity<>(newProduct, HttpStatus.OK);
//    }

    @GetMapping("/product/{productCode}")
    public ResponseEntity<Product> getProduct(@PathVariable String productCode) throws ProductNotFoundException {
        Product product = this.productService.getProductByProductCode(productCode);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/product/{productCode}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String productCode) throws ProductNotFoundException {
        Product product = this.productService.getProductByProductCode(productCode);
        for(String awsUrl : product.getProductImageUrl()){
            String deleteImgName= awsUrl.substring(awsUrl.lastIndexOf('/')+1);
            this.s3Services.deleteFile(deleteImgName);
        }
        Product returnValue=this.productService.deleteProduct(product.getId());
        return new ResponseEntity<>(returnValue,HttpStatus.OK);
    }


}
