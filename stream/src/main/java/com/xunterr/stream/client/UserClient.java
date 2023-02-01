package com.xunterr.stream.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user")
public interface UserClient {
}
