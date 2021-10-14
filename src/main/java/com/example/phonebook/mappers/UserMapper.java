package com.example.phonebook.mappers;

import com.example.phonebook.dto.UserDTO;
import com.example.phonebook.dto.UserResponseDTO;
import com.example.phonebook.dto.UserUpdateDTO;
import com.example.phonebook.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO userDTO);
    UserResponseDTO toDTO(User user);
    User toEntity(UserUpdateDTO userUpdateDTO);
}
