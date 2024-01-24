package hema.web.validation;


import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.proxy.ValidateRuleProxyConfiguration;


//@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        ValidateRule validateRule = new ValidateRuleProxyConfiguration().validateRule();

        validateRule.field("name")
                .after("date")
                .before("age")
                .max(20).min(10)
                .exists("users","name")
                .unique("users","name");

//        validateRule.rules().forEach((k,v) -> {
//            System.out.println(k);
//            System.out.println(v);
//        });


    }
}