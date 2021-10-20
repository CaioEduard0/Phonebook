package com.example.phonebook.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ConflictException extends RuntimeException {

    private String message;
}
