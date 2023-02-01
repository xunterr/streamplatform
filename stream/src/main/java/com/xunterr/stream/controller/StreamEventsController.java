package com.xunterr.stream.controller;

import com.xunterr.stream.model.StreamEvent;
import com.xunterr.stream.service.StreamService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class StreamEventsController {
	private StreamService service;

	@PostMapping(
			path = "/on-publish",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public void onPublish(StreamEvent event){
		service.getById(UUID.fromString(event.name()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleException(IllegalStateException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
