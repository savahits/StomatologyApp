package ru.shmelev.stomatologyapp.exception;

public class ClientDataMismatchException extends RuntimeException {
    public ClientDataMismatchException(String message) {
        super(message);
    }
}
