import hema.web.validation.RequestValidator;
import hema.web.validation.concerns.haystack.AttributeHaystack;
import hema.web.validation.concerns.haystack.MessageHaystack;
import hema.web.validation.contracts.ValidateRule;

public class ValidatorTest extends RequestValidator {

    @Override
    protected void rules(ValidateRule rule) {
        rule.field("name").required().string();
        rule.field("email").required().email();
        rule.field("phone").required().phone().unique("users", "phone");
    }

    @Override
    protected boolean authorize() {
        return true;
    }

    @Override
    protected void attributes(AttributeHaystack haystacks) {
        haystacks.add("name", "用户名")
                .add("email", "邮箱")
                .add("phone", "手机号");
    }

    @Override
    protected void messages(MessageHaystack haystacks) {
        haystacks.add("name*", closure -> (
                        closure.add("required", "xxx")
                                .add("string", "xxx")
                ))
                .add("email*", closure -> (
                        closure.add("email", "format")
                                .add("max", "max")
                ));
    }
}
