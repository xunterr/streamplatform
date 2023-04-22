package com.xunterr.stream.controller;

import com.xunterr.stream.dto.CreateStreamRequest;
import com.xunterr.stream.dto.CreateStreamResponse;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.dto.StreamDTO;
import com.xunterr.stream.dto.StreamDTOMapper;
import com.xunterr.stream.service.StreamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/streams")
@AllArgsConstructor
public class StreamController {
	StreamService streamService;
	StreamDTOMapper streamDTOMapper;

	@GetMapping
	public List<StreamDTO> getAll(){
		return streamService.getAll()
				.stream()
				.map(streamDTOMapper::toDto)
				.toList();
	}

	@GetMapping("/{id}")
	public StreamDTO get(@PathVariable String id){
		Stream result = streamService.getById(UUID.fromString(id));
		return streamDTOMapper.toDto(result);
	}

	@PostMapping
	@PreAuthorize("#streamDto.userId.toString() == authentication.principal")
	public CreateStreamResponse create(@Valid @RequestBody CreateStreamRequest request){
		Stream result = streamService.create(request);
		return new CreateStreamResponse(result.getId(), result.getCreatedDate(), result.getStreamKey());
	}
}
