package hema.web.validation.concerns;

import hema.web.contracts.http.HttpException;
import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.exception.InvalidArgumentException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

interface ValidateAttributes {

    /**
     * Validate that an value was "accepted"
     * This validation rule implies the value is "required".
     *
     * @param value T
     * @param <T>   Generic type.
     * @return Boolean
     */
    default <T> boolean validateAccepted(T value) {
        return validateRequired(value) && acceptable().contains(value);
    }

    default <T> boolean validateRequiredIf(T value, ValidateRule.Access access) throws HttpException {

        InvalidArgumentException.requireParameterCount(2, access.parameters(), "acceptedIf");

        return true;
    }

    /**
     * Validate that an value was "declined".
     * This validation rule implies the value is "required".
     *
     * @param value T
     * @param <T>   Generic type.
     * @return Boolean.
     */
    default <T> boolean validateDeclined(T value) {
        return validateRequired(value) && declined().contains(value);
    }

    private Set<Object> acceptable() {
        return Set.of("yes", "on", "1", 1, true, "true");
    }

    private Set<Object> declined() {
        return Set.of("no", "off", "0", 0, false, "false");
    }

    default <T> boolean validateRequiredUnless(T value, ValidateRule.Access access) throws HttpException {

        InvalidArgumentException.requireParameterCount(2, access.parameters(), "requiredUnless");

        return true;
    }


    default <T> boolean validateRequiredWith(T value, ValidateRule.Access access) {

        if (!allFailingRequired(access.parameters())) {
            return validateRequired(value);
        }

        return true;
    }


    default <T> boolean validateRequiredWithAll(T value, ValidateRule.Access access) {

        if (!anyFailingRequired(access.parameters())) {
            return validateRequired(value);
        }

        return true;
    }

    default <T> boolean validateRequiredWithOut(T value, ValidateRule.Access access) {

        if (anyFailingRequired(access.parameters())) {
            return validateRequired(value);
        }

        return true;
    }

    default <T> boolean validateRequiredWithOutAll(T value, ValidateRule.Access access) {

        if (allFailingRequired(access.parameters())) {
            return validateRequired(value);
        }

        return true;
    }

    /**
     * Determine if any of the given attributes fail the required test.
     *
     * @param attributes Object[]
     * @return Boolean
     */
    private boolean anyFailingRequired(Object[] attributes) {

        for (Object attribute : attributes) {
            if (attribute instanceof String && validateRequired(getValue((String) attribute))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine if all of the given attributes fail the required test.
     *
     * @param attributes Object[]
     * @return Boolean
     */
    private boolean allFailingRequired(Object[] attributes) {

        for (Object attribute : attributes) {
            if (attribute instanceof String && validateRequired(getValue((String) attribute))) {
                return false;
            }
        }

        return true;
    }

    <T> T getValue(String attribute);

    /**
     * Validate that an map has all of the given keys.
     *
     * @param value  T
     * @param access Access
     * @param <T>    Generic type.
     * @return Boolean
     */
    default <T> boolean validateRequiredMapKeys(T value, ValidateRule.Access access) {

        if (!validateMap(value)) {
            return false;
        }

        Map<?, ?> maps = ((Map<?, ?>) value);

        for (Object param : access.parameters()) {
            if (!maps.containsKey(param)) {
                return false;
            }
        }

        return true;
    }

    default <T> boolean validateMin(T value, ValidateRule.Access access) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, access.parameters(), "min");

        return getSize(value) >= access.first(Integer.class);
    }

    default <T> boolean validateMax(T value, ValidateRule.Access access) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, access.parameters(), "max");

        return getSize(value) <= access.first(Integer.class);
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

    default <T> boolean validateIn(T value, ValidateRule.Access access) {

        if (access.parameters().length == 0) {
            return false;
        }

        return Set.of(access.parameters()).contains(value);
    }

    /**
     * Validate that an value is a legality email.
     *
     * @param value String
     * @return Boolean.
     */
    default <T> boolean validateEmail(String value) {
        return Pattern.compile("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$").matcher(value).matches();
    }

    /**
     * Validate that an value is a legality phone.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validatePhone(String value) {

        if (value.length() != 11) {
            return false;
        }

        return Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$").matcher(value).matches();
    }

    /**
     * Validate that an value is a legality id card number.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateIdCard(String value) {
        return Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)").matcher(value).matches();
    }

    /**
     * Validate that an value is a legality bank card number.
     *
     * @param value String
     * @return Boolean
     */
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

    /**
     * Validate that an value is a JSON.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateJson(String value) {
        return (value.startsWith("{") && value.endsWith("}")) && isConvertibleJson(value);
    }

    /**
     * Check strings can be formatted by JSON.
     *
     * @param value String
     * @return Boolean
     */
    private boolean isConvertibleJson(String value) {
        try {
            new JSONObject(value);

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * Validate the size of an attribute.
     *
     * @param value  T
     * @param access Access
     * @param <T>    Generic type.
     * @return Boolean
     * @throws HttpException exception
     */
    default <T> boolean validateSize(T value, ValidateRule.Access access) throws HttpException {

        InvalidArgumentException.requireParameterCount(1, access.parameters(), "size");

        return BigInteger.valueOf((long) value).equals(access.first(BigInteger.class));
    }

    /**
     * Validate the attribute starts with a given substring.
     *
     * @param value  String
     * @param access Access
     * @return Boolean
     */
    default boolean validateStartWith(String value, ValidateRule.Access access) {
        return String.valueOf(value).startsWith(access.first(String.class));
    }

    /**
     * Validate the attribute ends with a given substring.
     *
     * @param value  String
     * @param access Access
     * @return Boolean
     */
    default boolean validateEndWith(String value, ValidateRule.Access access) {
        return String.valueOf(value).endsWith(access.first(String.class));
    }

    /**
     * Validate that an value is a valid lowercase.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateLowercase(String value) {
        return value.toLowerCase().equals(value);
    }

    /**
     * Validate that an value is a valid uppercase.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateUppercase(String value) {
        return value.toUpperCase().equals(value);
    }

    /**
     * Validate that an value is a valid Boolean.
     *
     * @param value T
     * @param <T>   Generic type.
     * @return Boolean
     */
    default <T> boolean validateBool(T value) {

        Set<Object> acceptable = Set.of(true, false, "true", "false", 1, 0, "1", "0");

        return validateRequired(value) && acceptable.contains(value);
    }

    /**
     * Validate that an value is a valid Integer.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateInteger(String value) {

        if (!validateNumeric(value)) {
            return false;
        }

        return isNegativeInteger(value) && !isDoubleInteger(value);
    }

    /**
     * Check that an value is a valid positive or negative integer.
     *
     * @param value String
     * @return Boolean
     */
    private boolean isNegativeInteger(String value) {
        return Pattern.compile("^[-+]?[1-9]\\d*|0$").matcher(value).matches();
    }

    /**
     * Check that an value is a valid float.
     *
     * @param value String
     * @return Boolean
     */
    private boolean isDoubleInteger(String value) {
        return Pattern.compile("^[1-9]\\d*(\\.\\d+)?|^0(\\.\\d+)?").matcher(value).matches();
    }

    /**
     * Validate that an value is a valid Numeric.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateNumeric(String value) {

        boolean isValid = true;

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * Validate that an value is a valid URL.
     *
     * @param value String
     * @return Boolean
     */
    default boolean validateUrl(String value) {
        try {
            return !URI.create(value).toURL().toString().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate that an value is a valid String.
     *
     * @param value T
     * @param <T>   Generic type
     * @return Boolean
     */
    default <T> boolean validateString(T value) {
        return value instanceof String;
    }

    /**
     * Validate that an value is a empty.
     *
     * @param value T
     * @param <T>   Generic type
     * @return Boolean
     */
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

    /**
     * Validate that an value is a valid Map.
     *
     * @param value T
     * @param <T>   Generic Type
     * @return Boolean
     */
    default <T> boolean validateMap(T value) {
        return value instanceof Map<?, ?>;
    }

    /**
     * Validate that an value is a valid Array.
     *
     * @param value T
     * @param <T>   Generic type.
     * @return Boolean
     */
    default <T> boolean validateArray(T value) {
        return value.getClass().isArray();
    }

    default <T> boolean validateTryEnum(T value, Class<T> type) {
        return type.isInstance(value);
    }
}
