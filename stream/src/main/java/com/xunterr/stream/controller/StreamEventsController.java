package com.xunterr.stream.controller;

import com.xunterr.stream.service.StreamService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class StreamEventsController {
	private StreamService service;

	@PostMapping("/on-publish")
	public void onPublish(@RequestBody String name){
		service.onPublishEvent(name);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleException(IllegalStateException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
