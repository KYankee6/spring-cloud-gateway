package com.example.apigatewayservice.filter;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
	Environment env;

	public AuthorizationHeaderFilter(Environment env) {
		super(Config.class);
		this.env = env;
	}

	// login -> token -> users( with token) -> header( include token)
	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			System.out.println("authorizationHeader = " + authorizationHeader);
			String jwt = authorizationHeader.replace("Bearer ", "");

			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);

			}
			return chain.filter(exchange);
		});

	}

	private boolean isJwtValid(String jwt) {
		String property = env.getProperty("token.secret");
		System.out.println("property = " + property);
		byte[] secretKeyBytes = Base64.getEncoder().encode(property.getBytes());
		SecretKey signingKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
		boolean returnValue = true;
		String subject = null;

		try {
			JwtParser jwtParser = Jwts.parser()
				.setSigningKey(signingKey)
				.build();

			subject = jwtParser.parseClaimsJws(jwt).getBody().getSubject();

		} catch (Exception e) {
			returnValue = false;
		}

		if (subject == null || subject.isEmpty()) {
			returnValue = false;
		}
		return returnValue;
	}

	// Mono(단일), Flux(여러) -> Spring WebFlux
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);

		log.error(err);
		return response.setComplete();
	}

	public static class Config {
	}

}
