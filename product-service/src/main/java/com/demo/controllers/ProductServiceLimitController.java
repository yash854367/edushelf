package com.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ProductLimitConfiguration;

@RestController
@RequestMapping("/api/product-service/limits")
public class ProductServiceLimitController {
	
	@Autowired
	private ProductLimitConfiguration limitConfiguration;
	
	@GetMapping
	public ProductLimitConfiguration getLimits() {
		return limitConfiguration;
	}

}
