package com.medilabo.client.UI.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.medilabo.client.UI.services.CookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieServiceImpl implements CookieService {

	private static final Logger logger = LogManager.getLogger(CookieServiceImpl.class);

	@Override
	public String getCookie(HttpServletRequest request) {
		try {
			Cookie[] cookies = request.getCookies();
			String valeurDuCookie = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("AuthorizationMedilabo")) {
						valeurDuCookie = cookie.getValue();
					}
				}
			}

			if (valeurDuCookie != null) {
				return "Basic " + valeurDuCookie;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("Error while getting cookie");
			throw new RuntimeException("Error while getting cookie");
		}
	}
}
