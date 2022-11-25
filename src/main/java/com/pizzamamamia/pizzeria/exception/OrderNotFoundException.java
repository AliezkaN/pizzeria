package com.pizzamamamia.pizzeria.exception;

public class OrderNotFoundException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Order is not found!";

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {return ErrorType.DATABASE_ERROR_TYPE;}
}
