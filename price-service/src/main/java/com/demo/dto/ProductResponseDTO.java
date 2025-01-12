package com.demo.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.demo.entity.Category;
import com.demo.entity.ProductVariant;

public class ProductResponseDTO {

	private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    private List<Category> categories = new ArrayList<>();
    private List<ProductVariant> variants = new ArrayList<>();
    
	public ProductResponseDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ProductVariant> getVariants() {
		return variants;
	}

	public void setVariants(List<ProductVariant> variants) {
		this.variants = variants;
	}
    
    
}
