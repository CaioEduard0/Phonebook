package com.example.phonebook.exceptions.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant timestamp;
	private Integer status;
	private String error;
	private Set<ErrorsDTO> errors;
	
	public ApiErrorDTO() {}
	
	public ApiErrorDTO(Instant timestamp, Integer status, String error, Set<ErrorsDTO> errors) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.errors = errors;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	

	public Set<ErrorsDTO> getErrors() {
		return errors;
	}

	public void setErrors(Set<ErrorsDTO> errors) {
		this.errors = errors;
	}	
}
