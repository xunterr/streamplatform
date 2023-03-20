package com.xunterr.stream.service;

import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.repository.StreamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StreamService {
	StreamRepository repository;

	public List<Stream> getAll(){
		return repository.findAll();
	}

	public Stream getById(UUID id){
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(id, "Stream not found"));
	}

	public Stream create(Stream stream) {
		return repository.saveAndFlush(stream);
	}
}
