package hema.web.validation.message;

import java.util.SplittableRandom;
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

        Pattern pattern = Pattern.compile(STR."^\{haystack}\\z", Pattern.UNICODE_CASE);

        Matcher matcher = pattern.matcher(needle);

        return matcher.matches();
    }

    public static String regexQuote(String haystack, String delimiter) {

        String regex = "[.\\\\+*?\\[\\]^$(){}=!<>|:-]";

        if (delimiter != null && !delimiter.isEmpty()) {
            regex += STR."|\{Pattern.quote(delimiter)}";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(haystack);

        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, STR."\\\\\{matcher.group()}");
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    public static String random(int length) {

        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder buffer = new StringBuilder();

        appendRandomChar(buffer, chars, length);

        return buffer.toString();
    }

    protected static void appendRandomChar(StringBuilder buffer, String chars, int length) {
        SplittableRandom random = new SplittableRandom();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            buffer.append(chars.charAt(number));
        }
    }

    public static String ucFirst(String value) {
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public static String implode(String separator, String[] array) {
        StringBuilder builder = new StringBuilder();
        append(builder, separator, array);
        return builder.toString();
    }

    protected static void append(StringBuilder builder, String separator, String[] array) {
        for (int i = 0; i < array.length; i++) {
            builder.append(array[i]);
            if (i != array.length - 1) {
                builder.append(separator);
            }
        }
    }
}
