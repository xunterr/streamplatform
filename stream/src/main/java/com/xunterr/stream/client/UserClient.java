package com.xunterr.stream.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user")
public interface UserClient {
	@RequestMapping(method = RequestMethod.GET, value = "api/v1/users/{id}/")
	String getUserById(@PathVariable(value = "id") Long id);
}
