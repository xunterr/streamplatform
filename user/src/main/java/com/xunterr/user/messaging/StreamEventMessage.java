package com.xunterr.user.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StreamEventMessage implements Serializable {
	private UUID streamId;
	private UUID userId;
}
