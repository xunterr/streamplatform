package com.xunterr.user.messaging;

import com.xunterr.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class StreamListener {
	private UserService service;

	@RabbitListener(queues = "#{streamStartQueue.name}")
	public void onStreamStart(StreamEventMessage event){
		log.info("RECEIVED: " + event.getUserId());
		service.setLive(event.getUserId(), true);
	}

	@RabbitListener(queues = "#{streamEndQueue.name}")
	public void onStreamEnd(StreamEventMessage event){
		log.info("RECEIVED: " + event.getUserId());
		service.setLive(event.getUserId(), false);
	}
}
