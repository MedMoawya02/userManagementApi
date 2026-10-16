package com.usermanagement.exception;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String email){
        super("L'email est déjà utilisé : " + email);
    }
}
