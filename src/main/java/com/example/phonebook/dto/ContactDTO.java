package com.example.phonebook.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ContactDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;
	
	@NotBlank(message = "{phone.not.blank}")
	@Size(min = 7, max = 20, message = "{phone.size}")
	private String phone;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
