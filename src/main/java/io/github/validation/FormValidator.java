package io.github.validation;

import io.github.validation.contracts.ValidatesWhenResolved;

public abstract class FormValidator implements ValidatesWhenResolved {

    /**
     * Define validate form rules.
     *
     */
    protected abstract void rules(ValidateRule rule);

    /**
     * Determine if the user is authorized to make this request.
     *
     * @return boolean
     */
    protected abstract boolean authorize();

    private boolean isSubClassOf(Class<?> type) {
        return type.isInstance(this);
    }
}
