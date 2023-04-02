package com.xunterr.stream.service;

import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.messaging.MessageType;
import com.xunterr.stream.messaging.StreamEventMessage;
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

	public void deleteById(UUID id){
		repository.deleteById(id);
	}

	public void stop(UUID id){
		Stream stream = getById(id);
		if(stream.isAutoDelete()){
			deleteById(stream.getId());
		}
		stream.setLive(false);
		producer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.END)
		);
	}

	public void start(UUID id){
		Stream stream = getById(id);
		stream.setLive(true);

		producer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.START)
		);
	}
}