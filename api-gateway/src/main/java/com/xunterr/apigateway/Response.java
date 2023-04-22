package com.xunterr.apigateway;

public record Response (
		boolean success,
		Object body
){}
