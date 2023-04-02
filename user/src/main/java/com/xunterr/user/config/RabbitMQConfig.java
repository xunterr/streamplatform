package com.xunterr.user.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
	@Value("${messaging.rabbitmq.topicExchangeName}")
	private String topicExchangeName;

	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public Queue streamStartQueue(){
		return new AnonymousQueue();
	}

	@Bean
	public Queue streamEndQueue(){
		return new AnonymousQueue();
	}

	@Bean
	public Binding startQueueBinding(TopicExchange topic,
							 Queue streamStartQueue) {
		return BindingBuilder.bind(streamStartQueue)
				.to(topic)
				.with("stream.start.#");
	}

	@Bean
	public Binding endQueueBinding(TopicExchange topic, Queue streamEndQueue) {
		return BindingBuilder.bind(streamEndQueue)
				.to(topic)
				.with("stream.end.#");
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
