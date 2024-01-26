package hema.web.validation.concerns;

import hema.web.validation.contracts.ValidateRule;

import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

interface ValidateAttributes {

    default <T> boolean validateAccepted(String attribute, T value) {

        Set<Object> acceptable = Set.of("yes", "on", "1", 1, true, "true");

        return validateRequired(attribute, value) && acceptable.contains(value);
    }

    default <T> boolean validateDeclined(String attribute, T value) {

        Set<Object> acceptable = Set.of("no", "off", "0", 0, false, "false");

        return validateRequired(attribute, value) && acceptable.contains(value);
    }

    default <T> boolean validateStartWith(String attribute, T value, ValidateRule.Access access) {

        if (!validateString(attribute, value)) {
            return false;
        }

        return String.valueOf(value).startsWith(access.first(String.class));
    }

    default <T> boolean validateEndWith(String attribute, T value, ValidateRule.Access access) {

        if (!validateString(attribute, value)) {
            return false;
        }

        return String.valueOf(value).endsWith(access.first(String.class));
    }

    default boolean validateLowercase(String attribute, String value) {
        return value.toLowerCase().equals(value);
    }

    default boolean validateUppercase(String attribute, String value) {
        return value.toUpperCase().equals(value);
    }

    default <T> boolean validateBool(String attribute, T value) {

        Set<Object> acceptable = Set.of(true, false, "true", "false", 1, 0, "1", "0");

        return validateRequired(attribute, value) && acceptable.contains(value);
    }

    default <T> boolean validateInteger(String attribute, T value) {

        if (!validateNumeric(value)) {
            return isNegativeInteger((String) value) || isDoubleInteger((String) value);
        }

        return true;
    }

    private boolean isNegativeInteger(String value) {
        String regex = "^[-+]?[1-9]\\d*|0$";

        return Pattern.compile(regex).matcher(value).matches();
    }

    private boolean isDoubleInteger(String value) {
        String regex = "^[1-9]\\d*(\\.\\d+)?|^0(\\.\\d+)?";

        return Pattern.compile(regex).matcher(value).matches();
    }

    default <T> boolean validateNumeric(String attribute, T value) {

        boolean isValid = true;

        String input = (String) value;

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isValid = false;
            }
        }

        return isValid;
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
