package com.medilabo.client.UI.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.medilabo.client.UI.services.CookieService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CookieService cookieService;

	@Test
	public void testHomeWithCookieTrue() throws Exception {
		when(cookieService.getCookie(any())).thenReturn(null);

		ResultActions resultActions = mockMvc.perform(get("/"));

		resultActions.andExpect(status().isOk()).andExpect(view().name("home"))
				.andExpect(model().attribute("cookie", false));

		verify(cookieService, times(1)).getCookie(any());
	}

	@Test
	public void testHomeWithCookieFalse() throws Exception {
		when(cookieService.getCookie(any())).thenReturn("Basic test");

		ResultActions resultActions = mockMvc.perform(get("/"));

		resultActions.andExpect(status().isOk()).andExpect(view().name("home"))
				.andExpect(model().attribute("cookie", true));
		
		verify(cookieService, times(1)).getCookie(any());
	}
}
