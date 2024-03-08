package hema.web.validation.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Str {

    public static boolean is(String[] haystack, String needle) {

        for (String value : haystack) {
            if (is(value, needle)) {
                return true;
            }
        }

        return false;
    }

    public static boolean is(String haystack, String needle) {

        if (haystack.equals(needle)) {
            return true;
        }

        haystack = Pattern.quote(haystack);
        haystack = haystack.replace("\\Q", "")
                .replace("\\E", "");

        haystack = haystack.replace("*", ".*");

        Pattern pattern = Pattern.compile("^" + haystack + "\\z", Pattern.UNICODE_CASE);

        Matcher matcher = pattern.matcher(needle);

        return matcher.matches();
    }

}