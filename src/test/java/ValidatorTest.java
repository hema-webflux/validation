import hema.web.validation.FormValidator;
import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.source.SimpleSource;
import hema.web.validation.contracts.source.Sourceable;

public class ValidatorTest extends FormValidator {

    @Override
    protected void rules(ValidateRule rule) {
        rule.add("name").required().string();
        rule.add("email").required().email();
        rule.add("phone").required().phone().unique("users", "phone");
    }

    @Override
    protected boolean authorize() {
        return true;
    }

    @Override
    protected Sourceable attributes(Sourceable store) {
        return store.add("name", "用户名")
                .add("email", "邮箱")
                .add("phone", "手机号");
    }

    @Override
    protected SimpleSource messages(SimpleSource store) {
        return store.add("name*", closure -> (
                closure.add("required", "xxx")
                        .add("string", "xxx")
        )).add("email*", closure -> (
                closure.add("email", "format")
                        .add("max", "max")
        ));
    }
}
