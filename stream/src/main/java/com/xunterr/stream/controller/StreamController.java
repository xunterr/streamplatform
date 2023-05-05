package com.xunterr.stream.controller;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.service.*;
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
	private final StreamService streamService;
	private final StreamDTOMapper streamDTOMapper;

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
	@PreAuthorize("hasRole('ROLE_ADMIN') " +
			"or #request.userId == authentication.principal " +
			"and hasPermission(#request.userId, 'create')")
	public CreateStreamResponse create(@Valid @RequestBody CreateStreamRequest request){
		Stream result = streamService.create(request);
		return new CreateStreamResponse(result.getId(), result.getCreatedDate(), result.getStreamKey());
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, 'delete')")
	public void delete(@PathVariable UUID id){
		streamService.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id, 'update')")
	public void update(@PathVariable UUID id, UpdateStreamRequest request){
		streamService.update(request, id);
	}
}
