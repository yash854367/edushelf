package com.demo.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.demo.dto.ProductResponseDTO;

@FeignClient(name="product-service")
public interface ProductServiceFeign {

	@GetMapping("/products/{id}")
    public ProductResponseDTO fetchProductById(@PathVariable long id);
}
