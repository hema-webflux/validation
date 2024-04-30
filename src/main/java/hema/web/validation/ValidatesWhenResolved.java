package hema.web.validation;

import hema.web.validation.concerns.Validator;
import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;

interface ValidatesWhenResolved {

    default void failedAuthorization() throws UnauthorizedException {
        throw new UnauthorizedException(401, "This action is unauthorized.");
    }

    default void failedValidation(Validator validator) throws ValidationException {
        throw new ValidationException(validator, 422, "Validation error.");
    }

    void validateResolved() throws ValidationException, UnauthorizedException;

}