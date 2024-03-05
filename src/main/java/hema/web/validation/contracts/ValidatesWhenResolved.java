package hema.web.validation.contracts;

import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;

public interface ValidatesWhenResolved {
    default void failedAuthorization() throws UnauthorizedException {
        throw new UnauthorizedException(401, "Unauthorized:");
    }

    default void failedValidation(Validator validator) throws ValidationException {
        throw new ValidationException(validator, 422, "Validation error.");
    }

    void validateResolved() throws ValidationException, UnauthorizedException;

}