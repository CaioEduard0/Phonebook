package com.example.phonebook.exceptions.dto;

public class ErrorsDTO {
	
	private String field;
	private String message;
	
	public ErrorsDTO() {}
	
	public ErrorsDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
