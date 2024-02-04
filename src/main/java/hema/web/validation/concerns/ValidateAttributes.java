package hema.web.validation.concerns;

import hema.web.validation.contracts.ValidateRule;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

interface ValidateAttributes {

    default <T> boolean validateAccepted(T value) {

        Set<Object> acceptable = Set.of("yes", "on", "1", 1, true, "true");

        return validateRequired(value) && acceptable.contains(value);
    }

    default <T> boolean validateDeclined(T value) {

        Set<Object> acceptable = Set.of("no", "off", "0", 0, false, "false");

        return validateRequired(value) && acceptable.contains(value);
    }

    default <T> boolean validateMin(T value) {


        return true;
    }

    default <T> boolean validateIn(T value, ValidateRule.Access access) {

        if (value == null) {
            return false;
        }

        if (access.parameters().length == 0) {
            return false;
        }

        return Set.of(access.parameters()).contains(value);
    }

    default <T> boolean validateEmail(T value) {

        if (!validateString(value)) {
            return false;
        }

        String regex = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";

        return Pattern.compile(regex).matcher((String) value).matches();
    }

    default <T> boolean validatePhone(T value) {

        if (!validateString(value)) {
            return false;
        }

        if (value.toString().length() != 11) {
            return false;
        }

        String  regex   = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\\\d{8}$";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher((String) value).matches();
    }

    default <T> boolean validateIdCard(T value) {

        if (!validateString(value)) {
            return false;
        }

        return Pattern.compile("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)").matcher((String) value).matches();
    }

    default <T> boolean validateBankCard(T value) {

        if (!validateString(value)) {
            return false;
        }

        String card = (String) value;

        if (card.length() < 15 || card.length() > 19) {
            return false;
        }

        char bit = getBankCardCheckCode(card);

        if (bit == 'N') {
            return false;
        }

        return card.charAt(card.length() - 1) == bit;
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

    default <T> boolean validateJson(T value) {
        if (!validateString(value)) {
            return false;
        }

        String source = (String) value;

        return isJsonStruct(source) && isConvertibleJson(source);
    }

    private boolean isConvertibleJson(String value) {
        try {
            new JSONObject(value);

            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    private boolean isJsonStruct(String value) {
        return value.startsWith("{") && value.endsWith("}");
    }

    default <T> boolean validateSize(T value, ValidateRule.Access access) {

        Integer size = access.first(Integer.class);

        if (validateString(value)) {
            return String.valueOf(value).length() == size;
        }

        if (validateNumeric(value) && validateInteger(value)) {
            return value == size;
        }

        if (validateArray(value)) {
            return Array.getLength(value) == size;
        }

        return false;
    }

    default <T> boolean validateArray(T value) {
        return value.getClass().isArray();
    }

    default <T> boolean validateStartWith(String attribute, T value, ValidateRule.Access access) {

        if (!validateString(value)) {
            return false;
        }

        return String.valueOf(value).startsWith(access.first(String.class));
    }

    default <T> boolean validateEndWith(String attribute, T value, ValidateRule.Access access) {

        if (!validateString(value)) {
            return false;
        }

        return String.valueOf(value).endsWith(access.first(String.class));
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

    default <T> boolean validateInteger(T value) {

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

    default <T> boolean validateNumeric(T value) {

        boolean isValid = true;

        String input = (String) value;

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isValid = false;
            }
        }

        return isValid;
    }

    default <T> boolean validateUrl(T value) {

        if (!validateString(value)) {
            return false;
        }

        try {
            return !URI.create((String) value).toURL().toString().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    default <T> boolean validateString(T value) {
        if (value == null) {
            return false;
        }

        return value instanceof String && value.toString().matches("^[a-zA-z]+$");
    }

    default <T> boolean validateRequired(T value) {

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

    default <T> boolean validateTryEnum(T value, Class<T> type) {
        return type.isInstance(value);
    }
}
