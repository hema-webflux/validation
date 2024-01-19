package hema.web.validation.contracts;

public interface Rule {

    <T> boolean passes(String attribute, T value);

    String message();
}
