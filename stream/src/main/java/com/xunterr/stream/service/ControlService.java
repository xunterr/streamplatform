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
public class ControlService {
	private final StreamService streamService;
	private final StreamMessageProducer messageProducer;

	public String start(String key){
		Stream stream = streamService.updateLive(key, true);
		messageProducer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.START)
		);
		return stream.getId().toString();
	}

	public void finish(String key){
		Stream stream = streamService.updateLive(key, true);
		if(stream.isAutoDelete()) {
			streamService.deleteById(stream.getId());
		}
		messageProducer.produceMessage(
				new StreamEventMessage(stream.getId(), stream.getUserID(), MessageType.END)
		);
	}
}
