package com.xunterr.stream.controller;


import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.model.StreamDTO;
import com.xunterr.stream.model.StreamEvent;
import com.xunterr.stream.service.StreamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@AllArgsConstructor
public class StreamEventsController {
	private StreamService service;

	@PostMapping(
			path = "/on-publish",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<HttpHeaders> onPublish(StreamEvent event, HttpServletResponse httpResponse) {
		StreamDTO stream = service.getById(UUID.fromString(event.name()));
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", stream.getUserId().toString());
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(IllegalStateException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
