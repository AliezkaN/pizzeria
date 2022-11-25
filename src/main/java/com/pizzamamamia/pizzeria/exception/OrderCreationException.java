package com.pizzamamamia.pizzeria.exception;

public class OrderCreationException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Order can't be created";

    public OrderCreationException(String message) {
        super(message);
    }

    public OrderCreationException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {return ErrorType.DATABASE_ERROR_TYPE;}
}
