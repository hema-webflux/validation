package io.github.validation;

import io.github.validation.contracts.ValidateRule;
import io.github.validation.contracts.ValidatesWhenResolved;

public abstract class FormValidator implements ValidatesWhenResolved {

    public void test(){

    }

    /**
     * Define validate form rules.
     *
     */
    protected abstract void rules(ValidateRule validateRule);

    /**
     * Determine if the user is authorized to make this request.
     *
     * @return boolean
     */
    protected abstract boolean authorize();

    public boolean isSubClassOf(Class<?> type) {
        return type.isInstance(this);
    }
}
