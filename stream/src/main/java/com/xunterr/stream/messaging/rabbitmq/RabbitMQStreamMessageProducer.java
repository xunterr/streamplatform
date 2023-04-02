package com.xunterr.stream.messaging.rabbitmq;

import com.xunterr.stream.messaging.StreamEventMessage;
import com.xunterr.stream.messaging.StreamMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class RabbitMQStreamMessageProducer implements StreamMessageProducer {
	private RabbitTemplate rabbitTemplate;
	private TopicExchange exchange;
	private RabbitMQStreamTopicProvider topicProvider;

	@Override
	public void produceMessage(StreamEventMessage message){
		rabbitTemplate.convertAndSend(exchange.getName(),
				topicProvider.getTopicNameByMessageType(message.getMessageType()),
				message);
	}
}
