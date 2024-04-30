package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;
import hema.web.validation.contracts.MessageBag;
import hema.web.validation.concerns.Validator;

final public class ValidationException extends HttpException {

    private final Validator validator;

    public ValidationException(Validator validator, int code, String message) {
        super(code, message);
        this.validator = validator;
    }

    public MessageBag errors() {
        return validator.errors();
    }
}
