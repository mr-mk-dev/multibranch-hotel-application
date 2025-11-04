package me.manishcodes.hotelapplication.exception;

public class HotelAppException extends Exception{

    private int statusCode;

    public HotelAppException(String message , int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
