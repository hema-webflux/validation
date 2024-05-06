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

    default <T> boolean validateAccepted(T value) {
        return validateRequired(value) && hasAcceptable(value);
    }

    default <T> boolean validateRequiredIf(T value, Object[] parameters) throws HttpException {

        InvalidArgumentException.requireParameterCount(2, parameters, "acceptedIf");

        return true;
    }

    private <T> boolean hasAcceptable(T value) {
        return contains(value, new Object[]{"yes", "on", "1", 1, true, "true"});
    }

    default <T> boolean validateDeclined(T value) {
        return validateRequired(value) && hasDeclined(value);
    }

    private <T> boolean hasDeclined(T value) {
        return contains(value, new Object[]{"no", "off", "0", 0, false, "false"});
    }

    private <T> boolean contains(T value, Object[] acceptable) {

        for (Object accept : acceptable) {
            if (value.equals(accept)) {
                return true;
            }
        }

        return false;
    }

    default <T> boolean before(T value, Object[] parameters) {

        if (!validateDate(value)) {
            return false;
        }

        return DateValidator.isTimeBeforeSpecificTime((String) value, String.valueOf(parameters[0]));
    }

    default <T> boolean after(T value, Object[] parameters) {
        return DateValidator.isTimeAfterSpecificTime((String) value, String.valueOf(parameters[0]));
    }

    default <T> boolean dateEquals(T value, Object[] parameters) {

        if (!validateDate(value)) {
            return false;
        }

        return DateValidator.isEquals((String) value, String.valueOf(parameters[0]));
    }

    default <T> boolean validateDate(T value) {

        if (!validateString(value)) {
            return false;
        }

        return DateValidator.isDateFormat(DateValidator.DEFAULT_FORMAT, String.valueOf(value));
    }

    default <T> boolean validateRequiredUnless(T value, Object[] parameters) throws HttpException {

        InvalidArgumentException.requireParameterCount(2, parameters, "requiredUnless");

        return true;
    }

    default <T> boolean validateRequiredWith(T value, Object[] parameters) {

        if (!allFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default <T> boolean validateRequiredWithAll(T value, Object[] parameters) {

        if (!anyFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default <T> boolean validateRequiredWithOut(T value, Object[] parameters) {

        if (anyFailingRequired(parameters)) {
            return validateRequired(value);
        }

        return true;
    }

    default <T> boolean validateRequiredWithOutAll(T value, Object[] parameters) {

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

    <T> T getValue(String attribute);

    default <T> boolean validateRequiredMapKeys(T value, Object[] parameters) {

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

    default <T> boolean validateMin(T value, Object[] parameters) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, parameters, "min");

        return getSize(value) >= (int) parameters[0];
    }

    default <T> boolean validateMax(T value, Object[] parameters) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, parameters, "max");

        return getSize(value) <= (int) parameters[0];
    }

    private <T> int getSize(T value) {

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

    default <T> boolean validateIn(T value, Object[] parameters) {

        if (parameters.length == 0) {
            return false;
        }

        return Set.of(parameters).contains(value);
    }

    default <T> boolean validateEmail(String value) {
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

    default <T> boolean validateSize(T value, Object[] parameters) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, parameters, "size");

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

    default <T> boolean validateBool(T value) {

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

    default <T> boolean validateString(T value) {
        return value instanceof String;
    }

    default <T> boolean validateRequired(T value) {

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

        if (value instanceof Collection<?> && ((Collection<?>) value).isEmpty()) {
            return false;
        }

        return true;
    }

    default <T> boolean validateMap(T value) {
        return value instanceof Map;
    }

    default <T> boolean validateArray(T value) {
        return value.getClass().isArray();
    }

    default <T> boolean validateTryEnum(T value, Class<T> type) {
        return type.isInstance(value);
    }

    boolean validatePresent(String attribute);
}
