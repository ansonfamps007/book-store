package com.book.store.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.book.store.bean.BookForm;
import com.book.store.model.Item;
import com.book.store.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryConsumer {

	private BookService bookService;

	@Autowired
	public InventoryConsumer(BookService bookService) {
		this.bookService = bookService;
	}

	@KafkaListener(topics = "inventory_items")
	public void receive(@Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partitionId,
			@Header(KafkaHeaders.OFFSET) long offset, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header(KafkaHeaders.RECEIVED_TIMESTAMP) String eventReceivedTime, @Payload String message) {
		log.info("Received Message in topic: {} for partition: {} with offset: {} and with message: {}", topic,
				partitionId, offset, message);
		try {

			ObjectMapper mapper = new ObjectMapper();
			Item item = mapper.readValue(message, Item.class);
			BookForm bookForm = new BookForm();
			bookForm.setAuthor(item.getBookAuthor());
			bookForm.setCategory(item.getBookCategory());
			bookForm.setDescription(item.getBookDescription());
			bookForm.setLanguage(item.getBookLanguage());
			bookForm.setTitle(item.getBookTitle());
			bookService.addBook(bookForm);
		} catch (JsonProcessingException e) {
			log.debug("Json Conversion failed InventoryConsumer Listener receive method - {}", e.getMessage());
		}
	}
}
