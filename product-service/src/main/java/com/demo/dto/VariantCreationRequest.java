package com.demo.dto;

import java.math.BigDecimal;
import java.util.Map;

public class VariantCreationRequest {
    private String sku;
    private Map<String, String> variantAttributes;
    private Integer stock;
    private BigDecimal additionalPrice;
    
	public VariantCreationRequest() {
		super();
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Map<String, String> getVariantAttributes() {
		return variantAttributes;
	}

	public void setVariantAttributes(Map<String, String> variantAttributes) {
		this.variantAttributes = variantAttributes;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public BigDecimal getAdditionalPrice() {
		return additionalPrice;
	}

	public void setAdditionalPrice(BigDecimal additionalPrice) {
		this.additionalPrice = additionalPrice;
	}
    
	
    

    // Getters and Setters
}
