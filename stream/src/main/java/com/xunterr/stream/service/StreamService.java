package com.xunterr.stream.service;

import com.xunterr.stream.exception.AlreadyExistsException;
import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.key.AESStreamKeyGenerator;
import com.xunterr.stream.messaging.StreamMessageProducer;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.repository.StreamRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StreamService {
	private final StreamRepository repository;
	private final AESStreamKeyGenerator keyGenerator;

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

	public Stream create(@Valid CreateStreamRequest request) {
		if(repository.findByUserID(request.getUserId()).isPresent()){
			throw new AlreadyExistsException("Stream with this userID already exists");
		}
		Stream newStream = new Stream(
				request.getUserId(), request.getTitle(),
				request.getDescription(), request.isAutoDelete(),
				keyGenerator);

		return repository.saveAndFlush(newStream);
	}

	public Stream update(UpdateStreamRequest request, UUID id){
		Stream stream = getById(id);
		stream.setTitle(request.getTitle());
		stream.setDescription(request.getDescription());
		return repository.saveAndFlush(stream);
	}

	public Stream updateLive(String streamKey, boolean live){
		Stream stream = getByStreamKey(streamKey);
		stream.setLive(live);
		return repository.saveAndFlush(stream);
	}

	public void deleteById(UUID id){
		repository.deleteById(id);
	}
}