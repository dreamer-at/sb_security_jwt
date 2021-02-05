package com.mysbdemos.security_v1_demo.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is meant to be thrown by the controller to produce a response for the client.
 * <p> It is meant to be thrown if you try to perform operations with a rest api.
 * <p> The response code produced is 409
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class IsAlreadyExistException extends RuntimeException {
    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public IsAlreadyExistException(final Class<?> clazz, final String fieldName, final Object fieldValue) {
        super(String.format("%s with %s:'%s' is already exist", clazz.getSimpleName(), fieldName, fieldValue));
        this.resourceName = clazz.getSimpleName();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
