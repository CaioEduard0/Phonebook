package com.example.phonebook.exceptions;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.phonebook.services.exceptions.ContactNotFoundException;
import com.example.phonebook.services.exceptions.EmailNotFoundException;
import com.example.phonebook.services.exceptions.RepeatedEmailException;
import com.example.phonebook.services.exceptions.RepeatedUsernameException;
import com.example.phonebook.services.exceptions.UserNotFoundException;
import com.example.phonebook.services.exceptions.UserWithContactsException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrorDTO> handlerMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		Set<ErrorsDTO> errors = exception.getBindingResult().getFieldErrors().stream().map(err -> errorsBuilder(err.getField(), err.getDefaultMessage())).collect(Collectors.toSet());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiErrorDTO> userNotFound(UserNotFoundException exception) {
		Set<ErrorsDTO> errors = Set.of(errorsBuilder("User", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseErrorBuilder(HttpStatus.NOT_FOUND, errors));
	}
	
	@ExceptionHandler(UserWithContactsException.class)
	public ResponseEntity<ApiErrorDTO> databaseException(UserWithContactsException exception) {
		Set<ErrorsDTO> errors = Set.of(errorsBuilder("Database", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	@ExceptionHandler(ContactNotFoundException.class)
	public ResponseEntity<ApiErrorDTO> contactNotFound(ContactNotFoundException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Contact", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseErrorBuilder(HttpStatus.NOT_FOUND, errors));
	}
	
	@ExceptionHandler(RepeatedUsernameException.class)
	public ResponseEntity<ApiErrorDTO> repeatedUsername(RepeatedUsernameException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Username", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(baseErrorBuilder(HttpStatus.CONFLICT, errors));
	}
	
	@ExceptionHandler(RepeatedEmailException.class)
	public ResponseEntity<ApiErrorDTO> repeatedEmail(RepeatedEmailException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("E-mail", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.CONFLICT).body(baseErrorBuilder(HttpStatus.CONFLICT, errors));
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ApiErrorDTO> emailNotFound(EmailNotFoundException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("E-mail", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiErrorDTO> forbidden(AccessDeniedException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Forbidden", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(baseErrorBuilder(HttpStatus.FORBIDDEN, errors));
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiErrorDTO> missingParameter(MissingServletRequestParameterException exception) {
		Set<ErrorsDTO> errors = Set.of(new ErrorsDTO("Missing Parameter", exception.getMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors));
	}
	
	private ErrorsDTO errorsBuilder(String field, String message) {
		return new ErrorsDTO(field, message);
	}
	
	private ApiErrorDTO baseErrorBuilder(HttpStatus status, Set<ErrorsDTO> errors) {
		return new ApiErrorDTO(Instant.now(), status.value(), status.name(), errors);
	}
}
