package com.medilabo.client.UI.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo.client.UI.Dto.UserLoginDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.LoginService;

import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginServiceImpl implements LoginService {

	private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

	@Autowired
	private GatewayProxy gatewayProxy;

	@Override
	public String login(UserLoginDto userLoginDto, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String credentials = userLoginDto.getUsername() + ":" + userLoginDto.getPassword();
			String base64Credentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

			String authorizationHeader = "Basic " + base64Credentials;

			ResponseEntity<String> responseEntity = gatewayProxy.login(authorizationHeader);

			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				Cookie cookie = new Cookie("AuthorizationMedilabo", base64Credentials);
				cookie.setPath("/");
				cookie.setMaxAge(3600);
				response.addCookie(cookie);
				logger.info("User " + userLoginDto.getUsername() + " logged in");
				logger.info("Cookie added to the response");
			}
		} catch (FeignException.Unauthorized unauthorizedException) {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password");
			logger.error("Invalid username or password");
			return "redirect:/login";
		}

		return "redirect:/";
	}

	@Override
	public String logout(HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie("AuthorizationMedilabo", "");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		} catch (Exception e) {
			logger.error("Error while logging out");
			return "redirect:error";
		}

		return "redirect:/";
	}
}
