package com.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import reactor.core.publisher.Mono;



@Component
public class GatewayAuthenticationFilter extends AbstractGatewayFilterFactory<GatewayAuthenticationFilter.Config> implements OrderedFilter {

	@Autowired
	private final RestTemplate restTemplate;

	@Autowired
	private RouterValidator routerValidator;

	public GatewayAuthenticationFilter(RestTemplate restTemplate) {
		super(Config.class);
		this.restTemplate = restTemplate;
	}
	
	@PostConstruct
	public void init() {
	    System.out.println("GatewayAuthenticationFilter initialized.");
	}
	
	

	public RouterValidator getRouterValidator() {
		return routerValidator;
	}

	public void setRouterValidator(RouterValidator routerValidator) {
		this.routerValidator = routerValidator;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}



	public static class Config {
		// Configuration properties for the filter (if needed)
	}

	@Override
	public GatewayFilter apply(Config config) {
		System.out.println("GatewayAuthenticationFilter is being used to validate the token");
		
	
		return (exchange, chain) -> {
			
			System.out.println("Incoming request URI: " + exchange.getRequest().getURI());
			System.out.println("Is Secured: " + routerValidator.isSecured.test(exchange.getRequest()));


			if (routerValidator.isSecured.test(exchange.getRequest())) {
				System.out.println("Secured route: Authentication required");
				// Authentication logic here

				System.out.println("checking auth header");
				// Extract Authorization header
				String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

				if (authHeader == null || !authHeader.startsWith("Bearer ")) {
					System.out.println("Missing or invalid Authorization header");
					return sendErrorResponse(exchange, "Authorization header is missing or invalid",
							HttpStatus.UNAUTHORIZED);
				}

				String token = authHeader.substring(7); // Extract the token after "Bearer "
				System.out.println("Authorization header received, token extracted: " + token);

				// Validate the token via customer-service
				try {
					validateTokenWithCustomerService(token);
					System.out.println("Token validated successfully. Proceeding with request.");
					
					
				} catch (HttpClientErrorException ex) {
					System.out.println("Token validation failed: " + ex.getMessage());
					return sendErrorResponse(exchange, "Token validation failed: " + ex.getResponseBodyAsString(),
							HttpStatus.UNAUTHORIZED);
				} catch (Exception ex) {
					System.out.println("Exception during token validation: " + ex.getMessage());
					return sendErrorResponse(exchange, "Token validation failed: " + ex.getMessage(),
							HttpStatus.UNAUTHORIZED);
				}

				// If validation succeeds, proceed with the request
				return chain.filter(exchange);

			} else {
				System.out.println("Public route: Skipping authentication");
				return chain.filter(exchange); // Skip authentication for public routes
			}

		};
	}

	private void validateTokenWithCustomerService(String token) {
//		String customerServiceUrl = "http://customer-service/customers/validate-token?token=" + token;

		String customerServiceUrl = "http://localhost:8087/customers/validate-token?token=" + token;

		// Send GET request to customer-service
		restTemplate.getForObject(customerServiceUrl, String.class);
	}

	private Mono<Void> sendErrorResponse(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
		exchange.getResponse().setStatusCode(httpStatus);
		return exchange.getResponse().setComplete();
	}
	
	@Override
	public int getOrder() {
	    return -1; // High precedence
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
}
