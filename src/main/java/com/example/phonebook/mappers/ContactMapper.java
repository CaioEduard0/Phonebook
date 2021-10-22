package com.example.phonebook.mappers;

import com.example.phonebook.dto.ContactDTO;
import com.example.phonebook.dto.ContactResponseDTO;
import com.example.phonebook.entities.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    Contact toEntity(ContactDTO contactDTO);
    ContactResponseDTO toDTO(Contact contact);
}
