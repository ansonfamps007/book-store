
package com.book.store.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.store.bean.BookForm;
import com.book.store.exception.ValidationException;
import com.book.store.response.ApiResponse;
import com.book.store.response.BookResponse;
import com.book.store.response.Data;
import com.book.store.service.BookService;
import com.book.store.service.WatchlistService;
import com.book.store.util.ApiConstants;
import com.book.store.util.JwtUtil;
import com.google.zxing.WriterException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/book")
@Slf4j
public class BookController {

	@Value("${app.feed-book-interval}")
	private String interval;

	@Value("${app.items-per-page}")
	private String itemsPerPage;

	@Value("${app.api-host}")
	private String apiHost;

	@Value("${app.cover-image-path}")
	private String coverImagePath;

	@Autowired
	private BookService bookService;

	@Autowired
	private WatchlistService watchlistService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 
	 * @param bookForm
	 * @param request
	 * @return
	 * @throws WriterException
	 */
	@PostMapping(value = "/add_book", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse addBook(@RequestBody @Valid BookForm bookForm, HttpServletRequest request)
			throws WriterException {

		Integer id = bookService.addBook(bookForm).getId();
		int width = 350;
		int height = 350;
		bookService.generateQRCodeImage(id, width, height);
		return ApiResponse.builder().error(false).message("Book added successfully !").build();

	}

	/**
	 * 
	 * @param bookForm
	 * @param request
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	@PostMapping(value = "/create_book", produces = {
			MediaType.APPLICATION_JSON_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApiResponse createBook(@ModelAttribute BookForm bookForm, HttpServletRequest request)
			throws WriterException {

		Integer id = bookService.addBook(bookForm).getId();
		int width = 350;
		int height = 350;
		String qrCode = bookService.generateQRCodeImage(id, width, height);
		if (null != bookForm.getCoverImage()) {
			bookService.saveCoverImage(bookForm.getCoverImage(), id);
		}
		return ApiResponse.builder().error(false).message("Book added successfully with QR Code : " + qrCode).build();

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/generate_pdf", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse generatePdf(HttpServletRequest request) {
		bookService.generatePdf();
		return ApiResponse.builder().error(false).message("http://" + apiHost + "/book/pdf/QrCodes.pdf").build();
	}

	/**
	 * 
	 * @param bookForm
	 * @return
	 */
	@DeleteMapping(value = "/delete_book/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse deleteBook(@PathVariable int id) {

		if (bookService.existsByBookId(id)) {
			bookService.deleteBook(id);
			return ApiResponse.builder().error(false).message("Book deleted successfully.").build();
		} else {
			throw new ValidationException("Book not found");
		}
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/get_book/{book_id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse getBook(@PathVariable(value = "book_id") int bookId, HttpServletRequest request) {
		String jwt = jwtUtil.getJwt(request);
		String userName = null;
		BookResponse book = bookService.getBookById(bookId, userName);
		Data bookData = new Data();
		bookData.setBook(book);
		return ApiResponse.builder().error(false).message("OK").data(bookData).build();
	}

	@PutMapping(value = "/update_book", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse updateBook(@RequestBody BookForm bookForm) {
		if (bookService.existsByBookId(bookForm.getId())) {
			bookService.updateBook(bookForm);
			return ApiResponse.builder().error(false).message("Book updated successfully.").build();
		} else {
			throw new ValidationException("Book not found");
		}
	}

	@PostMapping(value = "/edit_book", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public ApiResponse editBook(@ModelAttribute BookForm bookForm) {
		if (bookService.existsByBookId(bookForm.getId())) {
			bookService.updateBook(bookForm);
			bookService.saveCoverImage(bookForm.getCoverImage(), bookForm.getId());
			return ApiResponse.builder().error(false).message("Book updated successfully.").build();
		} else {
			throw new ValidationException("Book not found");
		}
	}

	/**
	 * 
	 * @param bookForm
	 * @return
	 */

	@GetMapping(value = "/search_book", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse searchBook(@RequestParam("page_no") int pageNo,
			@RequestParam(required = false, value = "keyword") String keyWord, HttpServletRequest request) {
		String jwt = jwtUtil.getJwt(request);
		String userName = null;
		Data searchData = bookService.getBookByKeyword(pageNo, keyWord, Integer.valueOf(itemsPerPage), userName);
		if (!ObjectUtils.isEmpty(searchData)) {
			log.debug("searchData : ", searchData);
			return ApiResponse.builder().error(false).message("OK").data(searchData).build();
		} else {
			throw new ValidationException(ApiConstants.NO_DATA);
		}
	}

	/**
	 * 
	 * @param bookId
	 * @param isApproved
	 * @param request
	 * @return
	 */
	@PutMapping(value = "/change_state/{book_id}/{is_approved}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse changeState(@PathVariable(value = "book_id") int bookId,
			@PathVariable(value = "is_approved") int isApproved, HttpServletRequest request) {

		String jwt = jwtUtil.getJwt(request);
		String userName = null;
		if (bookService.isBookAvailable(bookId) == 1) {
			bookService.changeState(bookId, userName, isApproved);
		} else {
			throw new ValidationException("Can't disable, the book is not returned !");
		}
		return ApiResponse.builder().error(false).message("Changed state successfully.").build();

	}

	@GetMapping(value = "/get_notifications/{page_no}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ApiResponse getNotifications(@PathVariable(value = "page_no") int pageNo, HttpServletRequest request) {

		String jwt = jwtUtil.getJwt(request);
		String userName = null;

		Data notificationData = bookService.getNotifications(userName, pageNo, Integer.valueOf(itemsPerPage));
		if (!ObjectUtils.isEmpty(notificationData)) {
			log.debug("notificationData : ", notificationData);
			return ApiResponse.builder().error(false).message("OK").data(notificationData).build();
		} else {
			throw new ValidationException("No new notifications");
		}
	}

}
