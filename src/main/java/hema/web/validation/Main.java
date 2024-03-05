package hema.web.validation;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

//        ValidateRule validateRule = new ValidateRuleProxyConfiguration().validateRule();
//
//        validateRule.field("name").after("date").before("age").max(20).min(10).exists("users", "name").unique("users", "name");
//
//        validateRule.field("email").required().email().unique("accounts", "email").tryEnum(Toggle.class);
//
//        Map<String, Set<ValidateRule.Access>> rules = validateRule.rules();
//
//        rules.get("name").forEach(ele ->System.out.println(ele.first()));
//        rules.get("email").forEach(ele ->System.out.println(ele.first()));

        String date = "2021-02-06 17:04:00";

//        String regex = "^(?:(?!0000)[0-9]{4}\\-(?:(?:0[13578]|1[02])(?:\\-0[1-9]|\\-[12][0-9]|\\-3[01])|(?:0[469]|11)(?:\\-0[1-9]|\\-[12][0-9]|\\-30)|02(?:\\-0[1-9]|\\-1[0-9]|\\-2[0-8]))|(?:(((\\d{2})(0[48]|[2468][048]|[13579][26])|(([02468][048])|([13579][26]))00))\\-02\\-29))$";
//
//        System.out.println(Pattern.compile(regex).matcher(date).find());

        System.out.println(parseDate(date));
    }

    public static Date parseDate(String inputDate) {

        String[] patterns = {
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy/MM/dd HH:mm",
                "yyyy-MM-dd",
                "yyyy/MM/dd",
                "yyyyMMdd"
        };
        SimpleDateFormat df = new SimpleDateFormat();
        for (String pattern : patterns) {
            df.applyPattern(pattern);
            df.setLenient(false);
            ParsePosition pos  = new ParsePosition(0);
            Date          date = df.parse(inputDate, pos);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    interface Validator {
    }

    @FunctionalInterface
    interface Closure {
        boolean callback(Validator validator);
    }

    final class Test implements Validator {

        private Set<Boolean> afters;

        public void after(Closure closure) {
            afters.add(closure.callback(this));
        }
    }

}