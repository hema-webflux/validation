package hema.web.validation.concerns.haystack;

import hema.web.contracts.anonymous.Nullable;
import hema.web.validation.contracts.Haystack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public class MessageHaystack implements Haystack<MessageHaystack, Object>, Nullable {

    private final Map<String, Object> haystacks;

    private final Set<String> fallbacks;

    private boolean nullable = false;

    public MessageHaystack(Map<String, Object> haystacks, Set<String> fallbacks) {
        this.haystacks = haystacks;
        this.fallbacks = fallbacks;
    }

    public MessageHaystack setNullable(boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    @Override
    public MessageHaystack add(String needle, Object value) {
        haystacks.put(needle, value);
        return this;
    }

    public MessageHaystack add(String needle, UnaryOperator<AttributeHaystack> haystack) {
        haystacks.put(needle, haystack.apply(new AttributeHaystack(new HashMap<>())));
        fallbacks.add(needle);
        return this;
    }

    @Override
    public Object getFromHaystack(String needle) {
        return haystacks.get(needle);
    }

    @Override
    public boolean hasNeedleInHaystack(String needle) {
        return fallbacks.contains(needle);
    }

    @Override
    public String[] needles() {
        return haystacks.keySet().toArray(new String[0]);
    }

    @Override
    public boolean isNullable() {
        return nullable;
    }
}
