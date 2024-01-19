import hema.web.validation.FormValidator;
import hema.web.validation.contracts.ValidateRule;
import org.springframework.context.ApplicationContext;

import static hema.web.validation.FormValidator.*;

import java.util.Map;

public class ValidatorTests extends FormValidator implements Message, Attribute {
    public ValidatorTests(ApplicationContext container) {
        super(container);
    }

    @Override
    protected ValidateRule rules(ValidateRule rule) {
        rule.field("name").required().string();
        rule.field("email").required().email();
        return rule.field("phone").required().phone().unique("users", "phone");
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
