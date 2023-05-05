package com.xunterr.stream.service;

import com.xunterr.stream.model.Stream;
import org.springframework.stereotype.Service;


@Service
public class StreamDTOMapper {
	public StreamDTO toDto(Stream stream) {
		return new StreamDTO(stream.getId(), stream.getUserID(),
				stream.getTitle(), stream.getDescription(), stream.isAutoDelete());
	}
}
