package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;

public final class InvalidArgumentException extends HttpException {
    public InvalidArgumentException(int code, String message) {
        super(code, message);
    }
}
