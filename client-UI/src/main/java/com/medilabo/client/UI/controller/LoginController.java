package com.medilabo.client.UI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo.client.UI.Dto.UserLoginDto;
import com.medilabo.client.UI.services.LoginService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@Autowired
	private LoginService loginService;

	@GetMapping("/login")
	public String login() {
		logger.info("GET /login");
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute UserLoginDto userLoginDto, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		logger.info("POST /login with body: " + userLoginDto.toString());
		return loginService.login(userLoginDto, response, redirectAttributes);
	}

	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		logger.info("GET /logout");
		return loginService.logout(response);
	}
}
