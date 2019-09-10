package com.demoecommerce.web.exception;

public class OrderCartEmptyException extends RuntimeException {

    public OrderCartEmptyException() { super();}
    public OrderCartEmptyException(String message) { super(message);}
}
