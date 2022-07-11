package com.book.store.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.book.store.bean.BookForm;
import com.book.store.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	public void testAddBook() throws Exception {

		BookForm bookForm = new BookForm();

		bookForm.setAuthor("DummyAuthor");
		bookForm.setCategory("DummyCategory");
		bookForm.setDescription("DummyDescription");
		bookForm.setLanguage("DummyLanguage");
		bookForm.setTitle("DummyTitle");

		bookService = mock(BookService.class);
		doNothing().when(bookService).addBook(bookForm);
		bookService.addBook(bookForm);
		verify(bookService, times(1)).addBook(bookForm);

		ObjectMapper mapper = new ObjectMapper();
		MvcResult result = mockMvc
				.perform(post("/api/book/add").content(mapper.writeValueAsString(bookForm))
						.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();
		assertEquals("Successfully Inserted !", result.getResponse().getContentAsString());
	}

}
