import hema.web.validation.FormValidator;
import hema.web.validation.contracts.ValidateRule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidatorTest extends FormValidator {

    @Override
    protected void rules(ValidateRule rule) {
        rule.add("name").required().string();
        rule.add("email").required().email();
        rule.add("phone").required().phone().unique("users", "phone");
    }

    @Override
    protected boolean authorize() {
        return false;
    }

    @Test
    public void test() {
        Assertions.assertEquals("name", "name");
    }

}
