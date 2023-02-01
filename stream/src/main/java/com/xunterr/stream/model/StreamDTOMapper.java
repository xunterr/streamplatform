package com.xunterr.stream.model;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class StreamDTOMapper implements Function<Stream, StreamDTO> {
	@Override
	public StreamDTO apply(Stream stream) {
		return new StreamDTO(stream);
	}
}
