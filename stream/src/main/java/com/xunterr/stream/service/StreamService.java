package com.xunterr.stream.service;

import com.xunterr.stream.client.UserClient;
import com.xunterr.stream.exception.EntityNotFoundException;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.model.StreamDTO;
import com.xunterr.stream.model.StreamDTOMapper;
import com.xunterr.stream.repository.StreamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StreamService {
	StreamRepository repository;
	UserClient userClient;
	StreamDTOMapper streamDTOMapper;

	public List<StreamDTO> getAll(){
		return repository.findAll()
				.stream().map(streamDTOMapper)
				.toList();
	}

	public StreamDTO getById(UUID id){
		Stream stream = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, "Stream not found"));
		return new StreamDTO(stream);
	}

	public StreamDTO create(StreamDTO request) {
		Stream stream = Stream.builder()
				.title(request.getTitle())
				.description(request.getDescription())
				.userID(request.getUid())
				.build();

		return new StreamDTO(repository.saveAndFlush(stream));
	}
}
