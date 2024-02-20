package com.medilabo.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRouteLocator {

	@Bean
	RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("patient-api",
						r -> r.path("/patient-api/**").filters(f -> f.stripPrefix(1)).uri("lb://patient-api"))
				.route("feedback-service",
						r -> r.path("/feedback-service/**").filters(f -> f.stripPrefix(1)).uri("lb://feedback-service"))
				.route("assessment-service",
						r -> r.path("/assessment-service/**").filters(f -> f.stripPrefix(1)).uri("lb://assessment-service"))
				.build();
	}
}