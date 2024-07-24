package com.tickettoride.exception;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException() {
        super("You better check your email or password!");
    }
}
