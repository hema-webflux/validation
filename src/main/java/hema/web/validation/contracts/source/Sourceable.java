package hema.web.validation.contracts.source;

public interface Sourceable {

    Sourceable add(String attribute, String value);

    boolean isEmpty();

    boolean hasKey(String value);

}
