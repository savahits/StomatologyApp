package ru.shmelev.stomatologyapp.exception;

public class DoctorHasAppointmentsException extends RuntimeException {
    public DoctorHasAppointmentsException(Long id) {
        super("Doctor with id=" + id + " has appointments");
    }
}