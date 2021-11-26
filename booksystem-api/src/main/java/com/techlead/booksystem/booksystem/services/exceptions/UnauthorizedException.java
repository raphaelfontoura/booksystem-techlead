package com.techlead.booksystem.booksystem.services.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String msg) {
        super(msg);
    }
}
