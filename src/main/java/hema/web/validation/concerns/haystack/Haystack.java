package hema.web.validation.concerns.haystack;

import hema.web.contracts.anonymous.Nullable;

import java.util.Map;

public sealed abstract class Haystack<T, R> implements Nullable permits AttributeHaystack, MessageHaystack {

    protected final Map<String, R> haystacks;

    private boolean nullable = false;

    public Haystack(Map<String, R> haystacks) {
        this.haystacks = haystacks;
    }

    public abstract T add(String needle, R value);

    public abstract R getFromHaystack(String needle);

    public abstract boolean hasNeedleInHaystack(String needle);

    public String[] needles() {
        return haystacks.keySet().toArray(new String[0]);
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }

    public Haystack<T, R> setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }
}
