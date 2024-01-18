package hema.web.validation.contracts;

import hema.web.validation.exception.UnauthorizedException;
import hema.web.validation.exception.ValidationException;

import java.util.Map;

public interface ValidatesWhenResolved {
    default void failedAuthorization() throws UnauthorizedException {
        throw new UnauthorizedException();
    }

    default void failedValidation(Validator validator) throws ValidationException {
        throw new ValidationException(validator);
    }

    interface Attribute {
        Map<String, String> attributes(Map<String, String> map);
    }

    interface Message {
        Map<String, String> messages(Map<String, String> map);
    }
}