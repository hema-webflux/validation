import hema.web.validation.concerns.ValidatorConfiguration;
import hema.web.validation.concerns.ValidateRule;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateRuleTest {

    private final ValidateRule rule = new ValidatorConfiguration(new ClassPathXmlApplicationContext()).validateRule();

    @Test
    public void testValidateRuleRulesIsNotEmpty() {
        rule.field("name").required().string().exists("users", "name")
                .field("email").required().string().email().unique("users", "email");

        rule.field("phone").rawRule("required|phone|unique:users,phone");

        assertFalse(rule.rules().isEmpty());
    }

}
