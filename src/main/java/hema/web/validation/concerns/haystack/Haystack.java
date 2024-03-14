package hema.web.validation.concerns.haystack;

import hema.web.contracts.anonymous.Nullable;

import java.util.Map;

public sealed abstract class Haystack<T> implements Nullable permits AttributeHaystack, MessageHaystack {

    protected final Map<String, T> haystacks;

    private boolean nullable = false;

    public Haystack(Map<String, T> haystacks) {
        this.haystacks = haystacks;
    }

    public abstract Haystack<T> add(String needle, T value);

    public abstract T getFromHaystack(String needle);

    public abstract boolean hasNeedleInHaystack(String needle);

    public String[] needles() {
        return haystacks.keySet().toArray(new String[0]);
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public Haystack<T> setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }
}
