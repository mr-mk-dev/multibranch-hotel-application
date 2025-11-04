package me.manishcodes.hotelapplication.exception;

import org.springframework.http.HttpStatus;

public class BookingConflictException extends HotelAppException {
    public BookingConflictException(String message) {
        super(message, HttpStatus.CONFLICT.value());
    }
}