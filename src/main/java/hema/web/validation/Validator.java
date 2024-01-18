package hema.web.validation;

import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.contracts.ValidatesWhenResolved;

import java.util.Map;

public abstract class Validator implements ValidatesWhenResolved {

    /**
     * Define validate form rules.
     */
    protected abstract void rules(ValidateRule rule);

    /**
     * Determine if the user is authorized to make this request.
     *
     * @return boolean
     */
    protected abstract boolean authorize();

    private <T> boolean isSubClassOf(Class<T> type) {
        return type.isInstance(this);
    }

}
