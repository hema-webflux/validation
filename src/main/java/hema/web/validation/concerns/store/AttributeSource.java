package hema.web.validation.concerns.store;

import hema.web.validation.contracts.source.Sourceable;

public class AttributeSource extends AbstractSource {

    @Override
    public Sourceable add(String attribute, String value) {

        source.put(attribute, value);

        return this;
    }
}
