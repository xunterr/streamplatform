package com.xunterr.stream.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateStreamResponse implements Serializable {
	private final UUID id;
	private final Instant createdDate;
	private final String streamKey;
}
