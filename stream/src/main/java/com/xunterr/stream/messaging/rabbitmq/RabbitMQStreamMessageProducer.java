package com.xunterr.stream.messaging.rabbitmq;

import com.xunterr.stream.messaging.StreamEventMessage;
import com.xunterr.stream.messaging.StreamMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Service
@AllArgsConstructor
public class RabbitMQStreamMessageProducer implements StreamMessageProducer {
	private RabbitTemplate rabbitTemplate;
	private TopicExchange exchange;

	@Value("#{${messaging.topics}}")
	private Map<String, String> topics;

	@Override
	public void produceMessage(StreamEventMessage message){
		String topicKey = message.getMessageType().toString().toLowerCase();
		rabbitTemplate.convertAndSend(
				exchange.getName(),
				topics.get(topicKey),
				message
		);
	}
}
