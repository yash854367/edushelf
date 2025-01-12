package com.demo.entities;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String sku;

    @ElementCollection
    private Map<String, String> variantAttributes; // E.g., {"Size": "M", "Color": "Red"}

    private Integer stock;
    private BigDecimal additionalPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference
    private Product product;

	public ProductVariant() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
    
    
}