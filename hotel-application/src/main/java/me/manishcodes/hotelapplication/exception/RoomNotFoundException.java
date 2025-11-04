package me.manishcodes.hotelapplication.exception;

import org.springframework.http.HttpStatus;

public class RoomNotFoundException extends HotelAppException{
    public RoomNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND.value());
    }
}
