package com.example.phonebook.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class ContactResponseDTO implements Serializable {

	private Long id;
	private String name;
	private String phone;
}
