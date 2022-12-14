package com.xunterr.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Response {
    boolean success;
    Map<String, Object> body;
}

