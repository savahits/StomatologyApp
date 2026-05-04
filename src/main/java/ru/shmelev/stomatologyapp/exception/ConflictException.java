package ru.shmelev.stomatologyapp.exception;

import jakarta.validation.constraints.NotBlank;

public class ConflictException extends AppException {
    public ConflictException(@NotBlank String s) {
        super(s);
    }
}
