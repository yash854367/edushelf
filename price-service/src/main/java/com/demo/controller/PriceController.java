package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.dto.ProductResponseDTO;
import com.demo.entity.Price;
import com.demo.feignclients.ProductServiceFeign;
import com.demo.repository.PriceRepository;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ProductServiceFeign productServiceFeign;
	
	
	@GetMapping("/{sku}")
	public Price findPriceBySku(@PathVariable String sku) {
		return priceRepository.findFirstBySku(sku);
	}
	
	
	@PostMapping
	public Price createPrice(@RequestBody Price price) {
		
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<ProductResponseDTO> response =  restTemplate.getForEntity("http://localhost:8080/products/" + price.getProductId(), ProductResponseDTO.class);
//		
//		ProductResponseDTO productResponse = response.getBody();
		
//		WebClient client = WebClient.builder()
//				  .baseUrl("http://localhost:8080/products/" + price.getProductId())
//				  .build();
//		
//		WebClient.ResponseSpec spec = client.get().retrieve();
//		ProductResponseDTO productResponse = spec.bodyToMono(ProductResponseDTO.class).block();
		
		ProductResponseDTO productResponse = productServiceFeign.fetchProductById(price.getProductId());
		
		if(productResponse!=null)
			return priceRepository.save(price);
	
		return null;
	}
}
