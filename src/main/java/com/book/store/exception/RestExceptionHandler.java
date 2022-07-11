
package com.book.store.exception;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.book.store.dto.ApiErrorResponse;

/**
 * 
 * The type Rest exception handler.
 */
@RestControllerAdvice
public class RestExceptionHandler {

	/**
	 * 
	 * Bad request exception handler api error response.
	 * 
	 * @param ex the ex
	 * @return the api error response
	 */
	@ExceptionHandler(value = { ValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse badRequestExceptionHandler(final ValidationException ex) {
		return ApiErrorResponse.builder().error(true).errorCode(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage())
				.build();
	}

	/**
	 * 
	 * Bad request exception handler api error response.
	 * 
	 * @param ex the ex
	 * @return the api error response
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ApiErrorResponse badRequestExceptionHandler(final IllegalArgumentException ex) {
		return ApiErrorResponse.builder().error(true).errorCode(HttpStatus.NOT_ACCEPTABLE.value()).message(ex.getMessage())
				.build();
	}

	/**
	 * 
	 * Method Argument Not Valid exception handler api error response.
	 * 
	 * @param ex the ex
	 * @return the api error response
	 */
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	public ApiErrorResponse methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException ex) {
		final String errorMsg = ex.getBindingResult().getFieldError().getField() + " - "
				+ ex.getBindingResult().getFieldError().getDefaultMessage();
		return ApiErrorResponse.builder().error(true).errorCode(HttpStatus.NOT_ACCEPTABLE.value()).message(errorMsg).build();
	}

	/**
	 * 
	 * Internal server exception handler api error response.
	 * 
	 * @param ex the ex @return the api error response
	 */

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse internalServerExceptionHandler(final Exception ex) {
		return ApiErrorResponse.builder().error(true).errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(ex.getMessage()).build();
	}

	@ExceptionHandler(value = { ServiceUnavailableException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse internalServerExceptionHandler(final ServiceUnavailableException ex) {
		return ApiErrorResponse.builder().error(true).errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(ex.getMessage()).build();
	}
}
