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
		if(stream.isAutoDelete()){
			streamService.deleteById(stream.getId());
		}
		stream.setLive(false);
		streamService.update(stream, stream.getId());
		return stream.getId().toString();
	}

	public void finish(String key){
		log.info("FINISHING...");
		Stream stream = streamService.getByStreamKey(key);
		stream.setLive(true);
		streamService.update(stream, stream.getId());
		producer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.END)
		);
	}
}
