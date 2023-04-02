package com.xunterr.stream.service;

import com.xunterr.stream.messaging.MessageType;
import com.xunterr.stream.messaging.StreamEventMessage;
import com.xunterr.stream.messaging.StreamMessageProducer;
import com.xunterr.stream.model.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {
	StreamService streamService;
	StreamMessageProducer producer;

	public String publish(String key){
		Stream stream = streamService.getByStreamKey(key);
		streamService.start(stream.getId());
		return stream.getId().toString();
	}

	public void finish(String key){
		Stream stream = streamService.getByStreamKey(key);
		streamService.stop(stream.getId());
		producer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.END)
		);
	}
}
