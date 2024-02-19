package com.medilabo.client.UI.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	@LoadBalanced
	WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager);
		oauth2Client.setDefaultOAuth2AuthorizedClient(true);
		oauth2Client.setDefaultClientRegistrationId("myoauth");
		return WebClient.builder().apply(oauth2Client.oauth2Configuration()).build();
	}

	@Bean
	@LoadBalanced
	WebClient.Builder webClientBuilder(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager);
		oauth2Client.setDefaultOAuth2AuthorizedClient(true);
		oauth2Client.setDefaultClientRegistrationId("myoauth");
		return WebClient.builder().apply(oauth2Client.oauth2Configuration());
	}
}
