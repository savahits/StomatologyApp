package ru.shmelev.stomatologyapp.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super(String.format("Username already exists: %s", username));
    }
}
