import hema.web.validation.concerns.ValidatorConfiguration;
import hema.web.validation.contracts.ValidateRule;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateRuleTest {

    private final ValidateRule validateRule = new ValidatorConfiguration(new ClassPathXmlApplicationContext()).validateRule();

    @Test
    public void testValidateRuleRulesIsNotEmpty() {
        validateRule.field("name").required().string().exists("users","name")
                .field("email").required().string().email().unique("users","email");

        assertFalse(validateRule.rules().isEmpty());
    }

}
