package com.medilabo.client.UI.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.medilabo.client.UI.services.impl.CookieServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class CookieServiceTest {

	@InjectMocks
	private CookieServiceImpl cookieService;

	@Mock
	private HttpServletRequest request;

	@Test
	public void testGetCookie() {
		Cookie[] cookies = { new Cookie("AuthorizationMedilabo", "testValue") };

		when(request.getCookies()).thenReturn(cookies);

		String result = cookieService.getCookie(request);

		assertEquals("Basic testValue", result);
	}

	@Test
	public void testGetCookieWhenCookieDoesNotExist() {
		when(request.getCookies()).thenReturn(null);

		String result = cookieService.getCookie(request);

		assertEquals(null, result);
	}

	@Test
	public void testGetCookieWhenCookieNull() {
		Cookie[] cookies = { new Cookie("SomeOtherCookie", null) };

		when(request.getCookies()).thenReturn(cookies);

		String result = cookieService.getCookie(request);

		assertEquals(null, result);
	}
}