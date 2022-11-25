package com.pizzamamamia.pizzeria.exception;

public class SuchCustomerAlreadyExistException extends ServiceException{

    private static final String DEFAULT_MESSAGE = "Such customer already exist";

    public SuchCustomerAlreadyExistException(String message) {
        super(message);
    }

    public SuchCustomerAlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DATABASE_ERROR_TYPE;
    }
}
