package com.xunterr.stream.service;

import com.xunterr.stream.client.UserClient;
import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.model.StreamRequest;
import com.xunterr.stream.repository.StreamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StreamService {
	StreamRepository repository;
	UserClient userClient;

	public List<Stream> getAll(){
		return repository.findAll();
	}

	public Stream getById(Long id){
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Stream not found"));
	}

	public Stream getByKey(String key){
		return repository.findByStreamKey(key).orElseThrow(() -> new EntityNotFoundException("Stream not found"));
	}

	public Stream create(StreamRequest request){

		Stream stream = Stream.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.uid(request.getUid())
				.streamKey(userClient.getStreamKey(request.getUid()))
				.build();

		return repository.saveAndFlush(stream);
	}

	public void onPublishEvent(String streamKey){
		Optional<Stream> stream = repository.findByStreamKey(streamKey);
		if(stream.isEmpty()){
			throw new IllegalStateException("Stream does not exists");
		}
	}

}
