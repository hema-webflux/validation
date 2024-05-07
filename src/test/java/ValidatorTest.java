import hema.web.validation.RequestValidator;
import hema.web.validation.Attribute;
import hema.web.validation.Message;
import hema.web.validation.concerns.ValidateRule;

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
    protected Attribute attributes() {
        return Attribute.make()
                .add("name", "用户名")
                .add("email", "邮箱")
                .add("phone", "手机号");
    }

    @Override
    protected Message messages() {
        return Message.make().add("name*", closure -> (
                closure.add("required", "xxx")
                        .add("string", "xxx")
        )).add("email*", closure -> (
                closure.add("email", "format")
                        .add("max", "max")
        )).add("phone.max", "phone max length 11");
    }
}
