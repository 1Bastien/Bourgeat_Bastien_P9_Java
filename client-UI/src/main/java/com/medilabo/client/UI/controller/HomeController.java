package com.medilabo.client.UI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private static final Logger logger = LogManager.getLogger(HomeController.class);

	@GetMapping("/")
	public String home(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		logger.info("GET /");

		model.addAttribute("loggin", false);

		if (oauth2User != null) {
			model.addAttribute("loggin", true);
		}

		return "home";
	}

}
