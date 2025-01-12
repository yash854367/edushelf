package com.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.ProductCreationRequest;
import com.demo.dto.VariantCreationRequest;
import com.demo.entities.Product;
import com.demo.entities.ProductType;
import com.demo.repositories.CategoryRepository;
import com.demo.repositories.ProductRepository;
import com.demo.repositories.ProductTypeRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    
    @GetMapping("/{id}")
    public Product fetchProductById(@PathVariable long id) {
    	
    	return productRepository.findById(id).orElse(null);
    	
    }

    @PostMapping
    public ResponseEntity<Product> createProductWithVariants(@RequestBody ProductCreationRequest request) {
        // Fetch the ProductType
        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                .orElseThrow(() -> new RuntimeException("ProductType not found with ID: " + request.getProductTypeId()));

        // Create the Product
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setProductType(productType);

        // Assign Categories
       // List<Category> categories = categoryRepository.findAllById(request.getCategories());
       // product.setCategories(categories);

        // Add Variants
        for (VariantCreationRequest variantRequest : request.getVariants()) {
            product.createVariant(
                variantRequest.getSku(),
                variantRequest.getVariantAttributes(),
                variantRequest.getStock(),
                variantRequest.getAdditionalPrice()
            );
        }

        // Save Product with Variants
        Product savedProduct = productRepository.save(product);

        return ResponseEntity.ok(savedProduct);
    }
}


