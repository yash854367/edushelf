package com.demo.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "productType_id", nullable = false)
    private ProductType productType; // Links product to its type

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
     )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductVariant> variants = new ArrayList<>();

    // Add a method to create a variant based on ProductType
    public ProductVariant createVariant(String sku, Map<String, String> variantAttributes, Integer stock, BigDecimal additionalPrice) {
        // Validate variant attributes against product type
        validateVariantAttributes(variantAttributes);

        ProductVariant variant = new ProductVariant();
        variant.setSku(sku);
        variant.setVariantAttributes(variantAttributes);
        variant.setStock(stock);
        variant.setAdditionalPrice(additionalPrice);
        variant.setProduct(this);

        this.variants.add(variant);
        return variant;
    }

    private void validateVariantAttributes(Map<String, String> variantAttributes) {
        for (AttributeDefinition attribute : productType.getAttributes()) {
            String attributeName = attribute.getName();
            String attributeValue = variantAttributes.get(attributeName);

            // Ensure required attributes are provided
            if (attributeValue == null) {
                throw new IllegalArgumentException("Missing value for required attribute: " + attributeName);
            }

            // If ENUM, validate allowed values
            if (attribute.getType() == AttributeType.ENUM) {
                if (!attribute.getAllowedValues().contains(attributeValue)) {
                    throw new IllegalArgumentException("Invalid value for attribute " + attributeName + ": " + attributeValue);
                }
            }
        }
    }
    
    
    

	public Product() {
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

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
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