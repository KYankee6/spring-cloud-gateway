package com.uicheon.userservice.security;

import static java.lang.Long.*;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uicheon.userservice.dto.UserDto;
import com.uicheon.userservice.service.UserService;
import com.uicheon.userservice.vo.RequestLogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final UserService userService;
	private final Environment env;

	public AuthenticationFilter(AuthenticationManager authenticationManager,
		UserService userService,
		Environment env) {
		super(authenticationManager);
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
		HttpServletResponse response) throws
		AuthenticationException {
		try {
			RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.getEmail(),
					creds.getPassword(),
					new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		String userName = ((User)authResult.getPrincipal()).getUsername();
		UserDto userDetails = userService.getUserDetailsByEmail(userName);
		String property = env.getProperty("token.secret");
		System.out.println("property = " + property);
		byte[] secretKeyBytes = Base64.getEncoder().encode(env.getProperty("token.secret").getBytes());

		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

		Instant now = Instant.now();

		String token = Jwts.builder()
			.subject(userDetails.getUserId())
			.expiration(Date.from(now.plusMillis(Long.parseLong(env.getProperty("token.expiration_time")))))
			.issuedAt(Date.from(now))
			.signWith(secretKey)
			.compact();

		response.addHeader("token", token);
		response.addHeader("userId", userDetails.getUserId());
	}
}
