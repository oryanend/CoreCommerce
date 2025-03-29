package com.oryanend.dscommerce.services.execeptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
