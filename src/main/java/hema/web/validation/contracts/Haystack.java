package hema.web.validation.contracts;

public interface Haystack<T, R> {

    T add(String needle, R value);

    R getFromHaystack(String needle);

    boolean hasNeedleInHaystack(String needle);

    String[] needles();
}
