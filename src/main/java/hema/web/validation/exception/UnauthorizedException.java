package hema.web.validation.exception;

import hema.web.contracts.http.HttpException;

final public class UnauthorizedException extends HttpException {
    public UnauthorizedException(int code, String message) {
        super(code, message);
    }
}
