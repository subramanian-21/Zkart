package com.zkart.utils.exceptions;

public class InitialAdminLoginException extends Exception{

    public InitialAdminLoginException(){
        super("Admin Password Has to be Changed");
    }

}
