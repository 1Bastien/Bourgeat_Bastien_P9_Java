package com.medilabo.client.UI.configuration;

import feign.Response;
import feign.FeignException;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == HttpStatus.UNAUTHORIZED.value()) {
			return new FeignException.Unauthorized("Unauthorized", response.request(), null, null);
		}
		return defaultErrorDecoder.decode(methodKey, response);
	}
}
