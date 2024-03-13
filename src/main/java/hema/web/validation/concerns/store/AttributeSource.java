package hema.web.validation.concerns.store;

public non-sealed class AttributeSource extends AbstractSource<AttributeSource, String> {

    @Override
    public AttributeSource add(String attribute, String value) {
        source.put(attribute, value);
        return this;
    }
}
