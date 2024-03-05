package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;
import hema.web.validation.contracts.Validator;
import hema.web.validation.support.ValidateMessageBag;

final public class ValidationException extends HttpException {

    private final Validator validator;

    public ValidationException(Validator validator, int code, String message) {
        super(code, message);
        this.validator = validator;
    }

    public ValidateMessageBag errors() {
        return validator.errors();
    }
}
