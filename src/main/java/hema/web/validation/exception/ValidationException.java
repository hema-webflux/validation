package hema.web.validation.exception;

import hema.web.validation.contracts.httpkernel.HttpExceptionInterface;
import hema.web.validation.contracts.Validator;

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
