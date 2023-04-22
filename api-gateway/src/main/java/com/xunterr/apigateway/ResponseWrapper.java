package com.xunterr.apigateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class ResponseWrapper implements RewriteFunction<JsonNode, JsonNode> {

	private ObjectMapper mapper;

	@Override
	public Publisher<JsonNode> apply(ServerWebExchange serverWebExchange, JsonNode jsonNode) {
		boolean isError = serverWebExchange.getResponse().getStatusCode().isError();
		return Mono.just(
				wrap(isError, jsonNode)
		);
	}

	public JsonNode wrap(boolean isError, JsonNode body){
		Response response = new Response(!isError, body);
		return mapper.convertValue(response, JsonNode.class);
	}
}
