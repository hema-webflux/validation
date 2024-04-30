package hema.web.validation.concerns.haystack;

import hema.web.validation.Attribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

final public class Message extends Haystack<Object> {

    private final Set<String> fallbacks;

    public Message(Map<String, Object> haystacks, Set<String> fallbacks, boolean anonymous) {
        super(haystacks, anonymous);
        this.fallbacks = fallbacks;
    }

    @Override
    public Message add(String needle, Object value) {
        haystacks.put(needle, value);
        return this;
    }

    public Message add(String needle, UnaryOperator<Attribute> haystack) {
        haystacks.put(needle, haystack.apply(new Attribute(new HashMap<>(), false)));
        fallbacks.add(needle);
        return this;
    }

    @Override
    public Object getFromHaystack(String needle) {

        if (needle.contains(".")) {
            String[] needles = needle.split("\\.");
            return ((Attribute) haystacks.get(needles[0])).getFromHaystack(needles[1]);
        }

        return haystacks.get(needle);
    }

    @Override
    public boolean hasNeedleInHaystack(String needle) {
        String[] needles = needle.split("\\.");
        return fallbacks.contains(needles[0]) && ((Attribute) getFromHaystack(needles[0])).hasNeedleInHaystack(needles[1]);
    }

    public static Message make() {
        return new Message(new HashMap<>(), new HashSet<>(), false);
    }

}
