package com.example.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;

	@Email(message = "{email.not.valid}")
	@NotBlank(message = "{email.not.blank}")
	private String email;

	@NotBlank(message = "{password.not.blank}")
	@Size(min = 6, max = 50, message = "{password.size}")
	private String password;

	@NotEmpty(message = "{authorities.not.empty}")
	private Set<SimpleGrantedAuthority> authorities;
}

