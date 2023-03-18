package com.xunterr.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	private final WebClient.Builder webClientBuilder;

	public AuthenticationFilter(WebClient.Builder webClientBuilder) {
		super(Config.class);
		this.webClientBuilder = webClientBuilder;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
				return onError(exchange, "No Authentication Header", HttpStatus.UNAUTHORIZED);
			}

			String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwtToken = authHeader.replace("Bearer", "");

			return webClientBuilder.build()
					.get()
					.uri("http://user:8087/api/v1/auth/token/validate?token=" + jwtToken)
					.retrieve().bodyToMono(TokenDetails.class)
					.map(tokenDetails -> {
						exchange.getResponse()
								.getHeaders()
								.add("Authorization", tokenDetails.id().toString());
						return exchange;
					}).flatMap(chain::filter);
		});
	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus statusCode) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(statusCode);

		return response.setComplete();
	}

	public static class Config {

	}
}
