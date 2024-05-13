package hema.web.validation.concerns;

import hema.web.contracts.nullable.Nullable;

import java.util.Map;

public abstract class Haystack<T> implements Nullable {

    protected final Map<String, T> haystacks;

    private final boolean anonymous;

    public Haystack(Map<String, T> haystacks, boolean anonymous) {
        this.haystacks = haystacks;
        this.anonymous = anonymous;
    }

    public abstract Haystack<T> add(String needle, T value);

    public abstract T getFromHaystack(String needle);

    public abstract boolean hasNeedleInHaystack(String needle);

    public String[] needles() {
        return haystacks.keySet().toArray(new String[0]);
    }

    @Override
    public boolean isAnonymous() {
        return anonymous;
    }
}
