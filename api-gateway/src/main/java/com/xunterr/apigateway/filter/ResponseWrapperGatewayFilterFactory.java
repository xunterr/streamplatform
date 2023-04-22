package com.xunterr.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.xunterr.apigateway.ResponseWrapper;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseWrapperGatewayFilterFactory extends AbstractGatewayFilterFactory<ResponseWrapperGatewayFilterFactory.Config> {

	private ModifyResponseBodyGatewayFilterFactory responseBodyGatewayFilterFactory;
	private ResponseWrapper wrapper;

	public ResponseWrapperGatewayFilterFactory(ModifyResponseBodyGatewayFilterFactory responseBodyGatewayFilterFactory, ResponseWrapper wrapper) {
		super(Config.class);
		this.responseBodyGatewayFilterFactory = responseBodyGatewayFilterFactory;
		this.wrapper = wrapper;
	}

	@Override
	public GatewayFilter apply(Config config) {
		return responseBodyGatewayFilterFactory
				.apply(c -> c.setRewriteFunction(JsonNode.class, JsonNode.class, wrapper));
	}

	static class Config {

	}
}
