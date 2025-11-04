package me.manishcodes.hotelapplication.exception;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends HotelAppException {
    public BookingNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
