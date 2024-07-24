package com.tickettoride.exception;

public class AuthenticationException extends RuntimeException{

    public AuthenticationException() {
        super("Oops! Something went wrong, check your credentials!");
    }

}
