package hema.web.validation.adapter;

import hema.web.validation.contracts.ValidateRule;

import java.util.Arrays;

class ArrayAccess implements Cloneable, ValidateRule.Access {

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
    public <T> T first(Class<T> kind) {
        return data.length == 1 ? null : kind.cast(data[1]);
    }

    @Override
    public String other() {
        return String.valueOf(data[0]);
    }

    @Override
    public Object[] parameters() {
        return Arrays.copyOfRange(data, 0, data.length - 1);
    }

}
