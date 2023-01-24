package com.xunterr.stream.client;

import lombok.AllArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user")
public interface UserClient {
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/streamKey")
	String getStreamKey(@PathVariable(value = "id") Long id);

}