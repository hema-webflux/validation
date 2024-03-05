package hema.web.validation.concerns;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

final class DateValidator {

    public final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static boolean isDateFormat(String dateFormat, String value) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

        simpleDateFormat.applyPattern(dateFormat);
        simpleDateFormat.setLenient(false);

        Date date = simpleDateFormat.parse(value, new ParsePosition(0));

        return date != null;
    }

    private interface DateTimeDiffer {

        default boolean isDifference(String haystack, String needle) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_FORMAT);

            try {
                return action(simpleDateFormat.parse(haystack), simpleDateFormat.parse(needle));
            } catch (ParseException e) {
                return false;
            }
        }

        boolean action(Date haystack, Date needle);

    }

    public static boolean isTimeBeforeSpecificTime(String haystack, String needle) {
        return ((DateTimeDiffer) Date::before).isDifference(haystack, needle);
    }

    public static boolean isTimeAfterSpecificTime(String haystack, String needle) {
        return ((DateTimeDiffer) Date::after).isDifference(haystack, needle);
    }

    public static boolean isEquals(String haystack, String needle) {
        return ((DateTimeDiffer) Date::equals).isDifference(haystack, needle);
    }

}
