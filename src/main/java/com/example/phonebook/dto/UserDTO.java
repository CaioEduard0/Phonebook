package com.example.phonebook.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.phonebook.entities.User;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;
	
	@Email(message = "{email.not.valid}")
	@NotBlank(message = "{email.not.blank}")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public User DtoToUser(UserDTO userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		return user;
	}
}
