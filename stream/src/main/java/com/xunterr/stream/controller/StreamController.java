package com.xunterr.stream.controller;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.model.StreamRequest;
import com.xunterr.stream.service.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/streams")
public class StreamController {

	@Autowired
	StreamService streamService;

	@GetMapping
	public List<Stream> getAll(){
		return streamService.getAll();
	}

	@GetMapping("/{id}")
	public Stream get(@PathVariable Long id){
		return streamService.getById(id);
	}

	@PostMapping
	public Stream create(@Valid @RequestBody StreamRequest stream){
		return streamService.create(stream);
	}
}
