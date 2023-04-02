package com.xunterr.stream.dto;

import com.xunterr.stream.dto.StreamDTO;
import com.xunterr.stream.model.Stream;
import org.springframework.stereotype.Service;


@Service
public class StreamDTOMapper {
	public StreamDTO toDto(Stream stream) {
		return new StreamDTO(stream.getId(), stream.getUserID(),
				stream.getTitle(), stream.getDescription());
	}

	public Stream toStream(StreamDTO dto){
		return Stream.builder()
				.userID(dto.getUserId())
				.title(dto.getTitle())
				.description(dto.getDescription())
				.build();
	}
}
