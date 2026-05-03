package ru.shmelev.stomatologyapp.exception;

public class NotFoundException extends AppException {
    public NotFoundException(String entity, Object id) {
        super(entity + " not found with id = " + id);
    }
}