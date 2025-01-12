package com.demo;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouterValidator {

	public static final List<String> publicEndpoints = List.of(
			"/customers/register",
			"/customers/login",
			"/products/{id}",
			"/eureka"
			);
	
	public Predicate<ServerHttpRequest> isSecured =
		    request -> {
		        boolean isSecured = !publicEndpoints.stream()
		            .anyMatch(uri -> request.getURI().getPath().startsWith(uri));
		        System.out.println("Request Path: " + request.getURI().getPath() + ", Is Secured: " + isSecured);
		        return isSecured;
		    };
}
