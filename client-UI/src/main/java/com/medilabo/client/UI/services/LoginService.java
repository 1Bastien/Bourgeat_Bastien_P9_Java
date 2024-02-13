package com.medilabo.client.UI.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo.client.UI.Dto.UserLoginDto;

import jakarta.servlet.http.HttpServletResponse;

@Service
public interface LoginService {

	String login(UserLoginDto userLoginDto, HttpServletResponse response, RedirectAttributes redirectAttributes);
	
	String logout(HttpServletResponse response);
}
