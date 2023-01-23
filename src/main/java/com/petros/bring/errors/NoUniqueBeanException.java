package com.petros.bring.errors;

public class NoUniqueBeanException extends RuntimeException {

    private static final String NO_UNIQUE_BEAN_EX_TEMPLATE = "No such bean loaded to context, found classes are %s";

    public NoUniqueBeanException(String message) {
        super(String.format(NO_UNIQUE_BEAN_EX_TEMPLATE, message));
    }
}
