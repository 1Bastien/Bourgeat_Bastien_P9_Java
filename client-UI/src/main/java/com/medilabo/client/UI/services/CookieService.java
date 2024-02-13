package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface CookieService {

	String getCookie(HttpServletRequest request);
}
