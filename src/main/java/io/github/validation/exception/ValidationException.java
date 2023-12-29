package io.github.validation.exception;

import io.github.validation.contracts.Validator;
import io.github.validation.contracts.httpkernel.HttpExceptionInterface;

final public class ValidationException extends Exception implements HttpExceptionInterface {

    private final Validator validator;

    public ValidationException(Validator validator) {
        this.validator = validator;
    }

    @Override
    public int getStatusCode() {
        return 422;
    }
}
