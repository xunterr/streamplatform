package com.xunterr.stream.controller;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.model.StreamDTO;
import com.xunterr.stream.service.StreamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/streams")
public class StreamController {

	@Autowired
	StreamService streamService;

	@GetMapping
	public List<StreamDTO> getAll(){
		return streamService.getAll();
	}

	@GetMapping("/{id}")
	public StreamDTO get(@PathVariable String id){
		return streamService.getById(UUID.fromString(id));
	}

	@PostMapping
	@PreAuthorize("hasRole('USER') and #stream.userId.toString() == authentication.principal")
	public StreamDTO create(@Valid @RequestBody StreamDTO stream){
		return streamService.create(stream);
	}
}
