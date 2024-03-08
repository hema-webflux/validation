import hema.web.validation.adapter.ValidateRuleProxyConfiguration;
import hema.web.validation.contracts.ValidateRule;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateRuleTests {

    private final ValidateRule validateRule = new ValidateRuleProxyConfiguration().validateRule();

    @Test
    public void testValidateRuleRulesIsNotEmpty() {
        validateRule.add("name").required().string().exists("users","name")
                .add("email").required().string().email().unique("users","email");

        assertFalse(validateRule.rules().isEmpty());
    }

}
