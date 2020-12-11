package com.book.store.controller;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.book.store.bean.BookForm;
import com.book.store.model.ItemRecords;
import com.book.store.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AmqReader {

	@Autowired
	private BookService bookService;

	@JmsListener(destination = "inventory_management.items_queue", containerFactory = "jmsFactory")
	public void receive(TextMessage message) throws JMSException, JsonMappingException, JsonProcessingException {
		log.info("Book store received message='{}'", message.getText());
		ObjectMapper mapper = new ObjectMapper();
		List<ItemRecords> itemRecords = mapper.readValue(message.getText(), new TypeReference<List<ItemRecords>>() {
		});;
		log.info("message123='{}'", itemRecords.getClass());
		if (!CollectionUtils.isEmpty(itemRecords)) {
			itemRecords.forEach(item -> {
				if ("book".equals(item.getItemCategory()) && null != item.getItemDetails()
						&& !item.getItemDetails().isBlank()) {
					try {
						ObjectMapper bookMapper = new ObjectMapper();
						BookForm bookForm = bookMapper.readValue(item.getItemDetails(), BookForm.class);
						System.out.println("bookForm--->"+bookForm);
						bookService.addBook(bookForm);
					} catch (Exception e) {
						log.error("Exception:" + e);
					}
				}
			});
		}
	}
}