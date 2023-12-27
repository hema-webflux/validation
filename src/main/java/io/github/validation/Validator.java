package io.github.validation;

import io.github.validation.contacts.Attribute;
import io.github.validation.contacts.Message;

public abstract class Validator {

    public Validator() {

    }

    protected abstract void rules();

    private void prepareValidator() {

        if (isSubClassOf(Attribute.class)) {

        }

        if (isSubClassOf(Message.class)) {

        }

    }

    public boolean isSubClassOf(Class<?> type) {
        return type.isInstance(this);
    }

}
