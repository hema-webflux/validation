package hema.web.validation.concerns.haystack;

import hema.web.contracts.anonymous.Nullable;
import hema.web.validation.contracts.Haystack;

import java.util.Map;

public class AttributeHaystack implements Haystack<AttributeHaystack, String>, Nullable {

    private final Map<String, String> haystacks;

    private boolean nullable = false;

    public AttributeHaystack(Map<String, String> haystacks) {
        this.haystacks = haystacks;
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public AttributeHaystack setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
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

    @Override
    public String[] needles() {
        return haystacks.keySet().toArray(new String[0]);
    }
}
