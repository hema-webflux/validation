package hema.web.validation.message;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Str {

    /**
     * Determine if a given string matches a given pattern.
     *
     * @param haystack String[]
     * @param needle   String
     * @return boolean
     */
    public static boolean is(String[] haystack, String needle) {

        for (String value : haystack) {
            if (is(value, needle)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine if a given string matches a given pattern.
     *
     * @param haystack String
     * @param needle   String
     * @return boolean
     */
    public static boolean is(String haystack, String needle) {

        if (haystack.equals(needle)) {
            return true;
        }

        haystack = regexQuote(haystack, "#");

        haystack = haystack.replace("\\*", ".*");

        Pattern pattern = Pattern.compile("^" + haystack + "\\z", Pattern.UNICODE_CASE);

        Matcher matcher = pattern.matcher(needle);

        return matcher.matches();
    }

    public static String regexQuote(String haystack, String delimiter) {

        String regex = "[.\\\\+*?\\[\\]^$(){}=!<>|:-]";

        if (delimiter != null && !delimiter.isEmpty()) {
            regex += "|" + Pattern.quote(delimiter);
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(haystack);

        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "\\\\" + matcher.group());
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    public static String random(int length) {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            buffer.append(chars.charAt(number));
        }

        return buffer.toString();
    }

    public static String ucFirst(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

}
