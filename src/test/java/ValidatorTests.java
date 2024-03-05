import hema.web.validation.FormValidator;
import hema.web.validation.contracts.ValidateRule;
import org.springframework.context.ApplicationContext;

public class ValidatorTests extends FormValidator {
    public ValidatorTests(ApplicationContext container) {
        super(container);
    }

    @Override
    protected void rules(ValidateRule rule) {
        rule.field("name").required().string();
        rule.field("email").required().email();
        rule.field("phone").required().phone().unique("users", "phone");
    }

    @Override
    protected boolean authorize() {
        return false;
    }
}
