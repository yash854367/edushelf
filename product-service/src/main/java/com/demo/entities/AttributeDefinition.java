package com.demo.entities;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AttributeDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Attribute name (e.g., "Size", "Color", "Material")

    @Enumerated(EnumType.STRING)
    private AttributeType type; // Type of the attribute (e.g., TEXT, NUMBER, ENUM)

    @ElementCollection
    private List<String> allowedValues; // Applicable only if type is ENUM

	public AttributeDefinition() {
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

	public AttributeType getType() {
		return type;
	}

	public void setType(AttributeType type) {
		this.type = type;
	}

	public List<String> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(List<String> allowedValues) {
		this.allowedValues = allowedValues;
	}

	public AttributeDefinition(String name, AttributeType type, List<String> allowedValues) {
		super();
		this.name = name;
		this.type = type;
		this.allowedValues = allowedValues;
	}
    
	
    
}
