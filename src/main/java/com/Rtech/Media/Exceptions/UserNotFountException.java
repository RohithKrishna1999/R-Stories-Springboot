package com.Rtech.Media.Exceptions;

public class UserNotFountException extends RuntimeException {
    public UserNotFountException(){
        super("User Not Found");
    }
}
