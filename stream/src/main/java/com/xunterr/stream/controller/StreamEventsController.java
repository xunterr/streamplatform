package com.xunterr.stream.controller;


import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.dto.RtmpStreamEvent;
import com.xunterr.stream.service.EventService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j
@AllArgsConstructor
public class StreamEventsController {
	private EventService service;

	@PostMapping(
			path = "/on-publish",
			consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public ResponseEntity<HttpHeaders> onPublish(RtmpStreamEvent event) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", service.publish(event.name()));
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	@PostMapping(
			path = "/on-publish-done",
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
