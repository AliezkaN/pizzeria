package com.pizzamamamia.pizzeria.exception;

public class PizzaNotFoundException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Pizza is not found!";

    public PizzaNotFoundException(String message) {
        super(message);
    }

    public PizzaNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {return ErrorType.DATABASE_ERROR_TYPE;}
}
