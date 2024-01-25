package hema.web.validation.proxy;

import hema.web.validation.contracts.ValidateRule;

final class ArrayAccess implements Cloneable, ValidateRule.Access {

    private Object[] data = null;

    @Override
    public ArrayAccess clone() {
        try {
            return (ArrayAccess) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public ValidateRule.Access setData(Object[] data) {
        this.data = data;
        return this;
    }

    @Override
    public String first() {
        return (String) data[0];
    }
}
