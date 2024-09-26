package com.chandrakanthrck.twilio_communication.config;

import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class GatewayConfig {

    private final RequestRateLimiterGatewayFilterFactory requestRateLimiterGatewayFilterFactory;

    public GatewayConfig(RequestRateLimiterGatewayFilterFactory requestRateLimiterGatewayFilterFactory) {
        this.requestRateLimiterGatewayFilterFactory = requestRateLimiterGatewayFilterFactory;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, KeyResolver userKeyResolver) {
        return builder.routes()
                .route("rate-limited-route", r -> r.path("/api/sms/**")
                        .filters(f -> f.requestRateLimiter(config -> config
                                .setRateLimiter(new RedisRateLimiter(1, 1)) // Configure RedisRateLimiter here
                                .setKeyResolver(userKeyResolver) // Set key resolver
                        ))
                        .uri("http://localhost:8080")  // Replace with your service URI
                )
                .build();
    }

    // Define a KeyResolver to identify users (e.g., by IP address)
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }
}
