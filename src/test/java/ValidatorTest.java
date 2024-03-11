import hema.web.validation.FormValidator;
import hema.web.validation.concerns.schema.Blueprint;
import hema.web.validation.contracts.ValidateRule;

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
    protected Blueprint attributes(Blueprint store) {
        return store.add("name", "用户名")
                .add("email", "邮箱")
                .add("phone", "手机号");
    }

    @Override
    protected Blueprint messages(Blueprint store) {
        return store.add("name.required", ":attribute 不能为空")
                .add("email*", closure -> (
                        closure.add("required", ":attribute 不能为空")
                                .add("max",":attribute ")
                ));

    }
}
