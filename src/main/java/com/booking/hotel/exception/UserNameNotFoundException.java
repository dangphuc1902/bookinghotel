package com.booking.hotel.exception;

public class UserNameNotFoundException extends RuntimeException{
    public UserNameNotFoundException(String messgae){
        super(messgae);
    }
}
