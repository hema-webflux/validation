package hema.web.validation.concerns.haystack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

public non-sealed class MessageHaystack extends Haystack<Object> {

    private final Set<String> fallbacks;

    public MessageHaystack(Map<String, Object> haystacks, Set<String> fallbacks) {
        super(haystacks);
        this.fallbacks = fallbacks;
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

        if (needle.contains(".")) {
            String[] needles = needle.split("\\.");
            return ((AttributeHaystack) haystacks.get(needles[0])).getFromHaystack(needles[1]);
        }

        return haystacks.get(needle);
    }

    @Override
    public boolean hasNeedleInHaystack(String needle) {
        String[] needles = needle.split("\\.");
        return fallbacks.contains(needles[0]) && ((AttributeHaystack) getFromHaystack(needles[0])).hasNeedleInHaystack(needles[1]);
    }

}
