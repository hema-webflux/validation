import hema.web.validation.Validator;
import hema.web.validation.contracts.ValidateRule;

import static hema.web.validation.Validator.*;

import java.util.Map;

public class ValidatorTests extends Validator implements Message, Attribute {
    @Override
    protected void rules(ValidateRule rule) {
        rule.target("name").required().string();
        rule.target("email").required().email();
        rule.target("phone").required().phone().unique("users", "phone");
    }

    @Override
    protected boolean authorize() {
        return false;
    }

    @Override
    public Map<String, String> attributes(Map<String, String> map) {
        return null;
    }

    @Override
    public Map<String, String> messages(Map<String, String> map) {
        return null;
    }
}
