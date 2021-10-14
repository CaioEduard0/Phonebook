package com.example.phonebook.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class ContactDTO implements Serializable {
	
	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;
	
	@NotBlank(message = "{phone.not.blank}")
	@Size(min = 7, max = 20, message = "{phone.size}")
	private String phone;
}
