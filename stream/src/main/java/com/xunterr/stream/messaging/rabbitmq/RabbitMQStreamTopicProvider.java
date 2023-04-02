package com.xunterr.stream.messaging.rabbitmq;

import com.xunterr.stream.messaging.MessageType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class RabbitMQStreamTopicProvider {
	private final static String BASE_TOPIC = "stream";

	public String getTopicNameByMessageType(MessageType type) {
		return String.format("%s.%s", BASE_TOPIC, type.name().toLowerCase());
	}
}
