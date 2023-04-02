package com.xunterr.stream.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {
	@Value("${messaging.rabbitmq.topicExchangeName}")
	private String topicExchangeName;

	private final CachingConnectionFactory cachingConnectionFactory;

	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public TopicExchange topicExchange(){
		return new TopicExchange(topicExchangeName);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter){
		RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory);
		template.setMessageConverter(converter);
		return template;
	}
}
