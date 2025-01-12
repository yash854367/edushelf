package com.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductCreationRequest {
    @Override
	public String toString() {
		return "ProductCreationRequest [name=" + name + ", description=" + description + ", price=" + price
				+ ", productTypeId=" + productTypeId + ", categories=" + categories + ", variants=" + variants + "]";
	}

	private String name;
    private String description;
    private BigDecimal price;
    private Long productTypeId;
    private List<Long> categories; // List of category IDs
    private List<VariantCreationRequest> variants;
    
	public ProductCreationRequest() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public List<VariantCreationRequest> getVariants() {
		return variants;
	}

	public void setVariants(List<VariantCreationRequest> variants) {
		this.variants = variants;
	}
    
    
    

    // Getters and Setters
}