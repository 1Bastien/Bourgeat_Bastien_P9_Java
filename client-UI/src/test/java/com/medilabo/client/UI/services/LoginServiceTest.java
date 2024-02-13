package com.medilabo.client.UI.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medilabo.client.UI.Dto.UserLoginDto;
import com.medilabo.client.UI.proxies.GatewayProxy;
import com.medilabo.client.UI.services.impl.LoginServiceImpl;

import feign.FeignException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

	@InjectMocks
	private LoginServiceImpl loginService;

	@Mock
	private GatewayProxy gatewayProxy;

	@Mock
	private HttpServletResponse response;

	@Mock
	private RedirectAttributes redirectAttributes;

	@Test
	public void testLoginSuccess() {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setUsername("username");
		userLoginDto.setPassword("password");

		ResponseEntity<String> successfulResponse = new ResponseEntity<>("", HttpStatus.OK);

		when(gatewayProxy.login(anyString())).thenReturn(successfulResponse);

		loginService.login(userLoginDto, response, redirectAttributes);

		verify(response, times(1)).addCookie(any(Cookie.class));
		verify(redirectAttributes, never()).addFlashAttribute(anyString(), anyString());

		assertEquals("redirect:/", loginService.login(userLoginDto, response, redirectAttributes));
	}

	@Test
	public void testLoginWithNonSuccessfulResponse() {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setUsername("username");
		userLoginDto.setPassword("password");

		ResponseEntity<String> successfulResponse = new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

		when(gatewayProxy.login(anyString())).thenReturn(successfulResponse);

		loginService.login(userLoginDto, response, redirectAttributes);

		verify(response, never()).addCookie(any(Cookie.class));
		verify(redirectAttributes, never()).addFlashAttribute(anyString(), anyString());

		assertEquals("redirect:/", loginService.login(userLoginDto, response, redirectAttributes));
	}

	@Test
	public void testLoginFailure() {
		UserLoginDto userLoginDto = new UserLoginDto();
		userLoginDto.setUsername("username");
		userLoginDto.setPassword("password");

		FeignException.Unauthorized unauthorizedException = mock(FeignException.Unauthorized.class);
		when(gatewayProxy.login(anyString())).thenThrow(unauthorizedException);

		loginService.login(userLoginDto, response, redirectAttributes);

		verify(redirectAttributes, times(1)).addFlashAttribute("error", "Invalid username or password");

		assertEquals("redirect:/login", loginService.login(userLoginDto, response, redirectAttributes));
	}

	@Test
	public void testLogout() {
		loginService.logout(response);
		
		verify(response, times(1)).addCookie(any(Cookie.class));

		assertEquals("redirect:/", loginService.logout(response));
	}

}
