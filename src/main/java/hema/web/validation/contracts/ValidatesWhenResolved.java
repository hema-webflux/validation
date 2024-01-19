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

    void validateResolved() throws ValidationException;


    interface Anonymous {
        default boolean isAnonymousClass() {
            return false;
        }
    }

    interface Attribute extends Anonymous {

        Map<String, String> attributes();

        class AnonymousAttribute implements Attribute {

            @Override
            public Map<String, String> attributes() {
                return null;
            }

            @Override
            public boolean isAnonymousClass() {
                return true;
            }
        }
    }

    interface Message extends Anonymous {

        Map<String, String> messages();

        class AnonymousMessage implements Message {

            @Override
            public Map<String, String> messages() {
                return null;
            }

            @Override
            public boolean isAnonymousClass() {
                return true;
            }
        }
    }
}