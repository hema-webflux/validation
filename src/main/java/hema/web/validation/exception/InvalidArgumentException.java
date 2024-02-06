package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;

public final class InvalidArgumentException extends HttpException {
    public InvalidArgumentException(int code, String message) {
        super(code, message);
    }

    public static void requireParameterCount(int count, Object[] parameters, String rule) throws HttpException {
        if (parameters.length < count) {
            throw new InvalidArgumentException(500, String.format("Validation rule %s requires at least %s parameters", rule, count));
        }
    }
}
