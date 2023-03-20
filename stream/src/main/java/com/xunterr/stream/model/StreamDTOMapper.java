package com.xunterr.stream.model;

import org.springframework.stereotype.Service;


@Service
public class StreamDTOMapper {
	public StreamDTO toDto(Stream stream) {
		return new StreamDTO(stream.getId(), stream.getUserID(),
				stream.getTitle(), stream.getDescription());
	}

	public Stream toStream(StreamDTO dto){
		return Stream.builder()
				.userID(dto.userId)
				.title(dto.title)
				.description(dto.description)
				.build();
	}
}
