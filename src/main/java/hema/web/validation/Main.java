package hema.web.validation;


import hema.web.validation.concerns.ValidatorConfiguration;
import hema.web.validation.contracts.Validator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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
    }


}