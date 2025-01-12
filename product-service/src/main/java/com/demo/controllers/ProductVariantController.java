package com.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entities.ProductVariant;
import com.demo.repositories.ProductVariantRepository;

@RestController
@RequestMapping("/product-variants")
public class ProductVariantController {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @PostMapping
    public ResponseEntity<ProductVariant> createProductVariant(@RequestBody ProductVariant productVariant) {
        return ResponseEntity.ok(productVariantRepository.save(productVariant));
    }

    @GetMapping
    public ResponseEntity<List<ProductVariant>> getAllVariants() {
        return ResponseEntity.ok(productVariantRepository.findAll());
    }

    @GetMapping("/{productId}/variants")
    public ResponseEntity<List<ProductVariant>> getVariantsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(productVariantRepository.findByProductId(productId));
    }
}

