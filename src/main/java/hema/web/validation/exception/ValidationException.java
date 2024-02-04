package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;
import hema.web.validation.contracts.Validator;

final public class ValidationException extends HttpException {

    private Validator validator;

    public ValidationException(Validator validator, int code, String message) {
        super(code, message);
        this.validator = validator;
    }
}
