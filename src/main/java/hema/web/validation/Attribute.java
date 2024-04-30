package hema.web.validation;

import hema.web.validation.concerns.haystack.Haystack;

import java.util.HashMap;
import java.util.Map;

final public class Attribute extends Haystack<String> {

    public Attribute(Map<String, String> haystacks, boolean anonymous) {
        super(haystacks, anonymous);
    }

    @Override
    public Attribute add(String needle, String value) {
        haystacks.put(needle, value);
        return this;
    }

    @Override
    public String getFromHaystack(String needle) {
        return haystacks.get(needle);
    }

    @Override
    public boolean hasNeedleInHaystack(String needle) {
        return haystacks.containsKey(needle);
    }

    public static Attribute make() {
        return new Attribute(new HashMap<>(), false);
    }
}
