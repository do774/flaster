package com.flaster.blog.security;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String token;

	public AuthenticationResponse() {
	}

	public AuthenticationResponse(String token) {
		this.token = token;
	}
}
