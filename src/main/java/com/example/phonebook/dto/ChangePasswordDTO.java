package com.example.phonebook.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.phonebook.entities.User;

public class UserCreatorDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;
	
	@Email(message = "{email.not.valid}")
	@NotBlank(message = "{email.not.blank}")
	private String email;
	
	@NotBlank(message = "{password.not.blank}")
	@Size(min = 6, max = 50, message = "{password.size}")
	private String password;
	
	@NotBlank(message = "{username.not.blank}")
	@Size(min = 2, max = 30, message = "{username.size}")
	private String username;
	
	private String authorities;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
}
