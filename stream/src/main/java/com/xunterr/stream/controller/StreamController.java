package com.xunterr.stream.controller;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.model.StreamDTO;
import com.xunterr.stream.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
	public StreamDTO get(@PathVariable UUID id){
		return streamService.getById(id);
	}

	@PostMapping
	public StreamDTO create(@Valid @RequestBody StreamDTO stream){
		return streamService.create(stream);
	}
}
