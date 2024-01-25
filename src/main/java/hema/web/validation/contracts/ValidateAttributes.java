package hema.web.validation.contracts;

import java.net.URI;
import java.util.Map;
import java.util.Set;

public interface ValidateAttributes {

    default <T> boolean validateAccepted(String attribute, T value) {

        Set<Object> acceptable = Set.of("yes", "on", "1", 1, true, "true");

        return validateRequired(attribute, value) && acceptable.contains(value);
    }

    default <T> boolean validateDeclined(String attribute, T value) {

        Set<Object> acceptable = Set.of("no", "off", "0", 0, false, "false");

        return validateRequired(attribute, value) && acceptable.contains(value);
    }

    default <T> boolean validateUrl(String attribute, T value) {

        if (!validateString(attribute, value)) {
            return false;
        }

        try {
            return !URI.create((String) value).toURL().toString().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    default <T> boolean validateString(String attribute, T value) {

        if (!(value instanceof String)) {
            return false;
        }

        return !((String) value).isEmpty() || !((String) value).isBlank();
    }

    default <T> boolean validateRequired(String attribute, T value) {

        if (value == null) {
            return false;
        }

        if (value instanceof String && ((String) value).trim().isEmpty()) {
            return false;
        }

        if (value instanceof Map<?, ?> && ((Map<?, ?>) value).isEmpty()) {
            return false;
        }

        return !(value instanceof Set<?>) || !((Set<?>) value).isEmpty();
    }

    default <T> boolean validateNumeric(T value) {
        return value instanceof Number;
    }

    default <T> boolean validateTryEnum(String attribute, T value, Class<T> type) {
        return type.isInstance(value);
    }
}
