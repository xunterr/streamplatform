package com.xunterr.stream.controller;


import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.service.ControlService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j
@AllArgsConstructor
public class StreamEventsController {
	private ControlService service;

	@PostMapping(
			path = "/on-publish",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<HttpHeaders> onPublish(RtmpStreamEvent event) {
		log.info("PUBLISHING...");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", service.start(event.name()));
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	@PostMapping(
			path = "/on-finish",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public void onFinish(RtmpStreamEvent event) {
		log.info("FINISHING...");
		service.finish(event.name());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> handleException(EntityNotFoundException e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
