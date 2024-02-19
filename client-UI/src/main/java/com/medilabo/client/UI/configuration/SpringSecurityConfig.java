package com.medilabo.client.UI.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/webjars/**", "/assets/**", "/ressources/**", "/static/**",
				"/css/**", "/js/**", "/images/**", "/favicon.ico");
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(
				authorizeRequests -> authorizeRequests.requestMatchers("/").permitAll().anyRequest().authenticated())
				.oauth2Login(oauth2Login -> oauth2Login.loginPage("/oauth2/authorization/myoauth"))
				.oauth2Client(Customizer.withDefaults())
				.logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
		return http.build();
	}

	private LogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
				this.clientRegistrationRepository);
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/");
		return oidcLogoutSuccessHandler;
	}

}
