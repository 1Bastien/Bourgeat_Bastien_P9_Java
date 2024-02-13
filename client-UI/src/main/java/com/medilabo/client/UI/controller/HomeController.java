package com.medilabo.client.UI.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.medilabo.client.UI.services.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

	private static final Logger logger = LogManager.getLogger(HomeController.class);

	@Autowired
	private CookieService cookieService;

	@GetMapping("/")
	public String home(Model model, HttpServletRequest request) {

		logger.info("GET /");

		Boolean cookie = false;
		if (cookieService.getCookie(request) != null) {
			cookie = true;
		}
		model.addAttribute("cookie", cookie);
		return "home";
	}

}
