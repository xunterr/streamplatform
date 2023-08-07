package com.xunterr.stream.service;

import com.xunterr.stream.exception.AlreadyExistsException;
import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.key.AESStreamKeyGenerator;
import com.xunterr.stream.messaging.MessageType;
import com.xunterr.stream.messaging.StreamEventMessage;
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
	private final StreamMessageProducer messageProducer;

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

	public Stream create(@Valid CreateStreamRequest request, UUID userID) {
		if(repository.findByUserID(userID).isPresent()){
			throw new AlreadyExistsException("Stream with this userID already exists");
		}
		Stream newStream = new Stream(
				userID, request.getTitle(),
				request.getDescription(), request.isAutoDelete(),
				keyGenerator);

		return repository.saveAndFlush(newStream);
	}

	public Stream updateInfo(@Valid UpdateStreamRequest request, UUID id){
		Stream stream = getById(id);
		stream.setTitle(request.getTitle());
		stream.setDescription(request.getDescription());
		return repository.saveAndFlush(stream);
	}

	private Stream updateLive(String streamKey, boolean live){
		Stream stream = getByStreamKey(streamKey);
		stream.setLive(live);
		return repository.saveAndFlush(stream);
	}

	public String handleStart(String key){
		Stream stream = updateLive(key, true);
		messageProducer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.START)
		);
		return stream.getId().toString();
	}

	public void handleFinish(String key){
		Stream stream = updateLive(key, false);
		if(stream.isAutoDelete()) {
			deleteById(stream.getId());
		}
		messageProducer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.END)
		);
	}

	public void deleteById(UUID id){
		repository.deleteById(id);
	}
}