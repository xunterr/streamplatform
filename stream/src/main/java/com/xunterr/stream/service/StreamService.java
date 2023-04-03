package com.xunterr.stream.service;

import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.messaging.StreamMessageProducer;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.repository.StreamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StreamService {
	StreamRepository repository;
	KeyService keyService;
	StreamMessageProducer producer;

	public List<Stream> getAll(){
		return repository.findAll();
	}

	public Stream getById(UUID id){
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(id, "Stream not found"));
	}

	public Stream getByStreamKey(String key){
		return repository.findByStreamKey(key)
				.orElseThrow(() -> new EntityNotFoundException("Stream not found"));
	}

	public Stream create(Stream stream) {
		stream.setStreamKey(keyService.generateKey());
		stream.setLive(false);
		stream.setCreatedDate(Instant.now());
		return repository.saveAndFlush(stream);
	}

	public void  update(Stream stream, UUID id){
		stream.setId(id);
		repository.save(stream);
	}

	public void deleteById(UUID id){
		repository.deleteById(id);
	}
}