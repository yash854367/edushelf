package com.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ecommerce.product")
public class ProductLimitConfiguration {
	

	private String defaultLimit;
	private String defaultCategoryLimit;

	public ProductLimitConfiguration() {
		super();
	}

	public String getDefaultLimit() {
		return defaultLimit;
	}

	public void setDefaultLimit(String defaultLimit) {
		this.defaultLimit = defaultLimit;
	}

	public String getDefaultCategoryLimit() {
		return defaultCategoryLimit;
	}

	public void setDefaultCategoryLimit(String defaultCategoryLimit) {
		this.defaultCategoryLimit = defaultCategoryLimit;
	}

	

}
