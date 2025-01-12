package com.demo;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private final GatewayAuthenticationFilter gatewayAuthenticationFilter;

    public AppConfig(@Lazy GatewayAuthenticationFilter gatewayAuthenticationFilter) {
        this.gatewayAuthenticationFilter = gatewayAuthenticationFilter;
    }

    @Bean
    public GlobalFilter globalAuthenticationFilter() {
        return (exchange, chain) -> {
            System.out.println("Global Filter: Incoming request URI: " + exchange.getRequest().getURI());
            return gatewayAuthenticationFilter.apply(new GatewayAuthenticationFilter.Config())
                    .filter(exchange, chain);
        };
    }
}
