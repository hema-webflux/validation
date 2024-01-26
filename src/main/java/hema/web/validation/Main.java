package hema.web.validation;


import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.proxy.ValidateRuleProxyConfiguration;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

      LocalDate locale =  LocalDate.parse("2011-01-01 13:51:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT));

      System.out.println(locale);

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


    }
}