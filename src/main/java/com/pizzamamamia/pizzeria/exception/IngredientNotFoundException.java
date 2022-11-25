package com.pizzamamamia.pizzeria.exception;

public class IngredientNotFoundException extends ServiceException {

    private static final String DEFAULT_MESSAGE = "Ingredient is not found!";

    public IngredientNotFoundException(String message) {
        super(message);
    }

    public IngredientNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    @Override
    public ErrorType getErrorType() {return ErrorType.DATABASE_ERROR_TYPE;}
}
