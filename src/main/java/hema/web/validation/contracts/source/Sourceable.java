package hema.web.validation.contracts.source;

public interface Sourceable<T, R> {

    /**
     * Add a custom name or rule message to the attribute.
     *
     * @param attribute Validate field.
     * @param value     Custom attribute name or rule validate message.
     * @return Return Rourceable interface reference.
     */
    T add(String attribute, String value);

    boolean isEmpty();

    boolean hasAttribute(String value);

    R getSource(String attribute);

}
