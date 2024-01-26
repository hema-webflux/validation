package hema.web.validation.proxy;

import hema.web.validation.contracts.ValidateRule;

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
    public String rule() {
        return (String) data[0];
    }

    @Override
    public Object[] parameters() {
        return null;
    }
}
