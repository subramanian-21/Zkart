package com.zkart.utils.exceptions;

public class UnknownUserException extends Exception{
    public UnknownUserException() {
        super("Unknown User Please login");
    }
}
