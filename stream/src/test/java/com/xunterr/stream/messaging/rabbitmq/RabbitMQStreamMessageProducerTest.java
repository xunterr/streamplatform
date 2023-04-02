package com.xunterr.stream.messaging.rabbitmq;

import com.xunterr.stream.messaging.MessageType;
import com.xunterr.stream.messaging.StreamEventMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RabbitMQStreamMessageProducerTest {
	@Mock
	RabbitTemplate rabbitTemplate;
	@Mock
	TopicExchange exchange;
	@Mock
	RabbitMQStreamTopicProvider topicProvider;

	@InjectMocks
	RabbitMQStreamMessageProducer underTest;

	@Test
	void whenProduceMessage_shouldProduceMessage() {
		//given
		StreamEventMessage streamEventMessage = new StreamEventMessage(
				UUID.randomUUID(),
				UUID.randomUUID(),
				MessageType.START
		);

		//when
		underTest.produceMessage(streamEventMessage);

		//then
		verify(rabbitTemplate).convertAndSend(any(), any(), eq(streamEventMessage));
	}


}