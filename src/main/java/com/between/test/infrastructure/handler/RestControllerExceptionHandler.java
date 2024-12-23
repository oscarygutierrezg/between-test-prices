package com.between.test.infrastructure.handler;

import com.between.test.domain.exception.PriceNoSuchElementException;
import com.between.test.infrastructure.adapter.input.rest.dto.model.response.ApiResponseErrorDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestControllerExceptionHandler {


	@ExceptionHandler(PriceNoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResponseErrorDto> handleEntityNotFoundException(PriceNoSuchElementException exception){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(
						createApiResponseErrorDto(HttpStatus.NOT_FOUND, Arrays.asList(exception.getMessage()))
						);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponseErrorDto> handleMethodArgumentNotValidException(ConstraintViolationException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(
						createApiResponseErrorDto(HttpStatus.BAD_REQUEST, exception.getConstraintViolations().stream()
								.map( error -> error.getPropertyPath()+" "+error.getMessage())
								.collect(Collectors.toList()))
						);

	}


	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<ApiResponseErrorDto> handleException(Exception exception){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(
						createApiResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.asList(exception.getMessage()))
						);

	}

	private ApiResponseErrorDto createApiResponseErrorDto(HttpStatus httpStatus,List<String> errors) {
		return ApiResponseErrorDto.builder()
				.code(httpStatus.value())
				.message(httpStatus.getReasonPhrase())
				.errors(errors)
				.build();
	}
}
