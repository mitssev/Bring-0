package com.petros.bring.errors;

public class NoSuchBeanException extends RuntimeException {
    private static final String NO_SUCH_BEAN_EX = "No such bean loaded to context";

    public NoSuchBeanException() {
        super(NO_SUCH_BEAN_EX);
    }

}
