package com.xunterr.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class CreateStreamDTO implements Serializable {
	private final UUID id;
	private final Instant createdDate;
	private final String streamKey;
}
