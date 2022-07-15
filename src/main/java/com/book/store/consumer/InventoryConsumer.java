package com.book.store.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryConsumer {

	@KafkaListener(topics = "inventory_items")
	public void receive(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
			@Header(KafkaHeaders.OFFSET) long offset, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) String eventReceivedTime, @Payload String message) {
		log.info("Received Message in topic: {} for partition: {} with offset: {} and with message: {}", topic,
				partitionId, offset, message);
	}
}
