package hema.web.validation.contracts;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ValidateAttributes {

    default <T> boolean validateAccepted(String attribute, T value) {

        Set<Object> acceptable = Set.of("yes", "on", "1", 1, true, "true");

        return validateRequired(attribute, value) && acceptable.contains(value);
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
}
