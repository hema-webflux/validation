package hema.web.validation;


import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.proxy.ValidateRuleProxyConfiguration;

import java.util.Map;
import java.util.Set;

//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ValidateRule validateRule = new ValidateRuleProxyConfiguration().validateRule();

        validateRule.field("name").after("date").before("age").max(20).min(10).exists("users", "name").unique("users", "name");

        validateRule.field("email").required().email().unique("accounts", "email").isEnum(Toggle.class);

        Map<String, Set<ValidateRule.Access>> rules = validateRule.rules();

        rules.get("name").forEach(ele ->System.out.println(ele.first()));
        rules.get("email").forEach(ele ->System.out.println(ele.first()));
    }

    enum Toggle {}
}