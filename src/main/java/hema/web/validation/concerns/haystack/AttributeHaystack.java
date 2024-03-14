package hema.web.validation.concerns.haystack;

import java.util.Map;

public non-sealed class AttributeHaystack extends Haystack<String> {

    public AttributeHaystack(Map<String, String> haystacks) {
        super(haystacks);
    }

    @Override
    public AttributeHaystack add(String needle, String value) {
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
}
