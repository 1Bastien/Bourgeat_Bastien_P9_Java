package com.medilabo.client.UI.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo.client.UI.Dto.UserLoginDto;
import com.medilabo.client.UI.services.LoginService;

import jakarta.servlet.http.HttpServletResponse;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LoginService loginService;

	@Test
	public void testGetLogin() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	public void testPostLogin() throws Exception {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setUsername("testUser");
		userLoginDto.setPassword("testPassword");

		HttpServletResponse response = mock(HttpServletResponse.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

		when(loginService.login(userLoginDto, response, redirectAttributes)).thenReturn("redirect:/");

		mockMvc.perform(post("/login").param("username", userLoginDto.getUsername()).param("password",
				userLoginDto.getPassword())).andExpect(status().isOk());
	}
	
	@Test
	public void testGetLogout() throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);

		when(loginService.logout(response)).thenReturn("redirect:/");

		mockMvc.perform(get("/logout")).andExpect(status().isOk());
	}
}
