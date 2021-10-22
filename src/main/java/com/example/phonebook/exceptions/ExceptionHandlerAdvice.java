package com.example.phonebook.exceptions;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.phonebook.exceptions.dto.ApiErrorDTO;
import com.example.phonebook.exceptions.dto.ErrorsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorDTO> validator(MethodArgumentNotValidException exception) {
		Set<ErrorsDTO> errors = exception.getBindingResult().getFieldErrors().stream().map(error -> errorsBuilder(error.getField(), error.getDefaultMessage())).collect(Collectors.toSet());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiErrorDTO> notFound(NotFoundException exception) {
		Set<ErrorsDTO> errors = Set.of(errorsBuilder("Not found", "The requested entity does not exist."));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseErrorBuilder(HttpStatus.NOT_FOUND, errors));
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiErrorDTO> badRequest(BadRequestException exception) {
		Set<ErrorsDTO> errors = Set.of(errorsBuilder("Bad request:", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiErrorDTO> conflict(ConflictException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Conflict", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(baseErrorBuilder(HttpStatus.CONFLICT, errors));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorDTO> forbidden(AccessDeniedException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Forbidden", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(baseErrorBuilder(HttpStatus.FORBIDDEN, errors));
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiErrorDTO> missingParameter(MissingServletRequestParameterException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Missing parameter", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	private ErrorsDTO errorsBuilder(String field, String message) {
		return new ErrorsDTO(field, message);
	}
	
	private ApiErrorDTO baseErrorBuilder(HttpStatus status, Set<ErrorsDTO> errors) {
		return new ApiErrorDTO(Instant.now(), status.value(), status.name(), errors);
	}
}
