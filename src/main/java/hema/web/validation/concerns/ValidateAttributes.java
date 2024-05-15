package hema.web.validation.concerns;

import hema.web.contracts.http.HttpException;
import hema.web.validation.exception.InvalidArgumentException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

interface ValidateAttributes {

    default boolean validateRequiredIf(Object value, Object[] parameters) throws HttpException {

        requireParameterCount(2, parameters, "acceptedIf");

        return true;
    }

    default boolean validateAccepted(Object value) {
        return validateRequired(value) && contains(value, new Object[]{"yes", "on", "1", 1, true, "true"});
    }

    default boolean validateDeclined(Object value) {
        return validateRequired(value) && contains(value, new Object[]{"no", "off", "0", 0, false, "false"});
    }

    private boolean contains(Object value, Object[] acceptable) {

        for (Object accept : acceptable) {
            if (value.getClass().equals(String.class) && ((String) value).equalsIgnoreCase(accept.toString())) {
                return true;
            } else if (value.equals(accept)) {
                return true;
            }
        }

        return false;
    }

    default boolean before(Object value, Object[] parameters) {

        if (!validateDate(value)) {
            return false;
        }

        return DateValidator.isTimeBeforeSpecificTime((String) value, String.valueOf(parameters[0]));
    }

    default boolean after(Object value, Object[] parameters) {
        return DateValidator.isTimeAfterSpecificTime((String) value, String.valueOf(parameters[0]));
    }

    default boolean dateEquals(Object value, Object[] parameters) {

        if (!validateDate(value)) {
            return false;
        }

        return DateValidator.isEquals((String) value, String.valueOf(parameters[0]));
    }

    default boolean validateDate(Object value) {

        if (!validateString(value)) {
            return false;
        }

        return DateValidator.isDateFormat(DateValidator.DEFAULT_FORMAT, String.valueOf(value));
    }

    default boolean validateRequiredUnless(Object value, Object[] parameters) throws HttpException {

        requireParameterCount(2, parameters, "requiredUnless");

        return true;
    }

    default boolean validateRequiredWith(Object value, Object[] parameters) {

        if (!allFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default boolean validateRequiredWithAll(Object value, Object[] parameters) {

        if (!anyFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default boolean validateRequiredWithOut(Object value, Object[] parameters) {

        if (anyFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default boolean validateRequiredWithOutAll(Object value, Object[] parameters) {

        if (allFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    private boolean anyFailingRequired(Object[] attributes) {

        for (Object attribute : attributes) {
            if (attribute instanceof String && validateRequired(getValue((String) attribute))) {
                return true;
            }
        }

        return false;
    }

    private boolean allFailingRequired(Object[] attributes) {

        for (Object attribute : attributes) {
            if (attribute instanceof String && validateRequired(getValue((String) attribute))) {
                return false;
            }
        }

        return true;
    }

    default boolean validateRequiredMapKeys(Object value, Object[] parameters) {

        if (!validateMap(value)) {
            return false;
        }

        Map<String, ?> maps = ((Map<String, ?>) value);

        for (Object param : parameters) {
            if (!maps.containsKey((String) param)) {
                return false;
            }
        }

        return true;
    }

    default boolean validateMin(Object value, Object[] parameters) throws HttpException {

        requireParameterCount(1, parameters, "min");

        return getSize(value) >= (int) parameters[0];
    }

    default boolean validateMax(Object value, Object[] parameters) throws HttpException {

        requireParameterCount(1, parameters, "max");

        return getSize(value) <= (int) parameters[0];
    }

    private int getSize(Object value) {

        if (validateArray(value)) {
            return Array.getLength(value);
        }

        if (validateMap(value)) {
            return ((Map<?, ?>) value).size();
        }

        if (validateString(value) && validateNumeric((String) value)) {
            return (int) value;
        }

        return String.valueOf(value).trim().length();
    }

    default boolean validateAfterOrEqual(String attribute, Object value, Object[] parameters) {

        requireParameterCount(1, parameters, "afterOrEqual");

        return true;
    }

    default boolean validateIn(Object value, Object[] parameters) {

        if (parameters.length == 0) {
            return false;
        }

        return Set.of(parameters).contains(value);
    }

    default boolean validateEmail(String value) {
        return Pattern
                .compile("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$")
                .matcher(value)
                .matches();
    }

    default boolean validatePhone(String value) {

        if (value.length() != 11) {
            return false;
        }

        return Pattern
                .compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$")
                .matcher(value)
                .matches();
    }

    default boolean validateIdCard(String value) {
        return Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)").matcher(value).matches();
    }

    default boolean validateBankCard(String value) {

        if (value.length() < 15 || value.length() > 19) {
            return false;
        }

        char bit = getBankCardCheckCode(value);

        if (bit == 'N') {
            return false;
        }

        return value.charAt(value.length() - 1) == bit;
    }

    private char getBankCardCheckCode(String bankCard) {
        if (bankCard == null || bankCard.trim().isEmpty() || !bankCard.matches("\\d+")) {
            return 'N';
        }
        char[] chs     = bankCard.trim().toCharArray();
        int    luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    default boolean validateJson(String value) {
        try {
            new JSONObject(value);

            return value.startsWith("{") && value.endsWith("}");
        } catch (JSONException e) {
            return false;
        }
    }

    default boolean validateSize(Object value, Object[] parameters) throws HttpException {

        requireParameterCount(1, parameters, "size");

        return BigInteger.valueOf((long) value).equals(parameters[0]);
    }

    default boolean validateStartWith(String value, Object[] parameters) {
        return String.valueOf(value).startsWith(String.valueOf(parameters[0]));
    }

    default boolean validateEndWith(String value, Object[] parameters) {
        return value.endsWith(String.valueOf(parameters[0]));
    }

    default boolean validateLowercase(String value) {
        return value.toLowerCase().equals(value);
    }

    default boolean validateUppercase(String value) {
        return value.toUpperCase().equals(value);
    }

    default boolean validateBool(Object value) {

        Set<Object> acceptable = Set.of(true, false, "true", "false", 1, 0, "1", "0");

        return validateRequired(value) && acceptable.contains(value);
    }

    default boolean validateInteger(String value) {

        if (!validateNumeric(value)) {
            return false;
        }

        return isNegativeInteger(value) && !isDoubleInteger(value);
    }

    private boolean isNegativeInteger(String value) {
        return Pattern.compile("^[-+]?[1-9]\\d*|0$").matcher(value).matches();
    }

    private boolean isDoubleInteger(String value) {
        return Pattern.compile("^[1-9]\\d*(\\.\\d+)?|^0(\\.\\d+)?").matcher(value).matches();
    }

    default boolean validateNumeric(String value) {

        boolean isValid = true;

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    default boolean validateUrl(String value) {
        try {
            return !URI.create(value).toURL().toString().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    default boolean validateString(Object value) {
        return value instanceof String;
    }

    default boolean validateRequired(Object value) {

        if (Objects.isNull(value)) {
            return false;
        }

        if (value instanceof String && (value.toString().trim().isEmpty())) {
            return false;
        }

        if (validateArray(value) && Array.getLength(value) == 0) {
            return false;
        }

        if (validateMap(value) && ((Map<?, ?>) value).isEmpty()) {
            return false;
        }

        return true;
    }

    default boolean validateMap(Object value) {
        return value instanceof Map;
    }

    default boolean validateArray(Object value) {
        return value.getClass().isArray();
    }

    default boolean validateTryEnum(Object value, Class type) {
        return type.isInstance(value);
    }

    private Object[] convertValuesToBoolean(String[] values) {
        return Arrays.stream(values).map(value ->
                switch (value.toLowerCase()) {
                    case "true" -> true;
                    case "false" -> false;
                    default -> value;
                }).toArray(Object[]::new);
    }

    private String[] convertValuesToNull(String[] values) {
        return Arrays.stream(values)
                .map(value -> value.equalsIgnoreCase("null") ? null : value)
                .toArray(String[]::new);
    }

    default boolean validateSame(Object value, Object[] parameters) {

        requireParameterCount(1, parameters, "same");

        Object other = getValue((String) parameters[0]);

        return other.equals(value);
    }

    private boolean compare(Date first, Date second, String operator) throws InvalidArgumentException {
        return switch (operator) {
            case "<" -> first.after(second);
            case ">" -> first.before(second);
            case "<=" -> first.after(second) || first.equals(second);
            case ">=" -> first.before(second) || first.equals(second);
            case "=" -> first.equals(second);
            default -> throw new InvalidArgumentException();
        };
    }

    private void requireParameterCount(int count, Object[] parameters, String rule) throws InvalidArgumentException {
        if (Array.getLength(parameters) < count) {
            throw new InvalidArgumentException(500, STR."Validation rule \{rule} requires at least \{count} parameters");
        }
    }

    private Object trim(Object value) {
        return value.getClass().equals(String.class) ? value.toString().trim() : value;
    }

    private boolean isSameType(Object first, Object second) {
        return first.getClass().getTypeName().equals(second.getClass().getTypeName());
    }

    void shouldBeNumeric(String attribute, String rule);

    Object getValue(String attribute);

    boolean validatePresent(String attribute);
}
