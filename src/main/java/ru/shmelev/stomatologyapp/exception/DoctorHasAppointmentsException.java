package ru.shmelev.stomatologyapp.exception;

public class DoctorHasAppointmentsException extends RuntimeException {
    public DoctorHasAppointmentsException(Long id) {
        super("У этого доктора есть запись");
    }
}