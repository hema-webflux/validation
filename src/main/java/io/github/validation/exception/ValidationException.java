package io.github.validation.exception;

final public class ValidationException extends Exception {

    public int getStatusCode() {
        return 422;
    }

}
