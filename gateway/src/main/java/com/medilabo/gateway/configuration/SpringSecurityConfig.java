package com.medilabo.gateway.configuration;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class SpringSecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		return http.csrf(csrf -> csrf.disable()).httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.builder().username("admin").password(passwordEncoder().encode("admin"))
				.roles("USER", "ADMIN").build();
		return new MapReactiveUserDetailsService(user);
	}

	@Bean
	RouterFunction<ServerResponse> customEndpoint() {
		return route(GET("/login"), this::handleCustomRequest);
	}

	private Mono<ServerResponse> handleCustomRequest(ServerRequest request) {
		ReactiveAuthenticationManager authManager = authenticationManager();
		return request.principal()
				.flatMap(principal -> authManager.authenticate(
						new UsernamePasswordAuthenticationToken(principal.getName(), principal.getName())))
				.flatMap(authentication -> ServerResponse.ok().build())
				.switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
	}

	@Bean
	ReactiveAuthenticationManager authenticationManager() {
		UserDetails user = User.builder().username("admin").password(passwordEncoder().encode("admin"))
				.roles("USER", "ADMIN").build();

		MapReactiveUserDetailsService userDetailsRepository = new MapReactiveUserDetailsService(user);

		UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
				userDetailsRepository);
		authenticationManager.setPasswordEncoder(passwordEncoder());
		return authenticationManager;
	}
}