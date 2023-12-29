package io.github.validation.contracts;

import io.github.validation.exception.UnauthorizedException;
import io.github.validation.exception.ValidationException;

public interface ValidatesWhenResolved {
    default void failedAuthorization() throws UnauthorizedException {
        throw new UnauthorizedException();
    }

    default void failedValidation(Validator validator) throws ValidationException {
        throw new ValidationException(validator);
    }
}