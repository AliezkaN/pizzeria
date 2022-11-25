package com.pizzamamamia.pizzeria.exception;


public class CustomerNotFoundException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Customer is not found!";

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
