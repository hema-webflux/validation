package hema.web.validation.contracts;

@FunctionalInterface
public interface ValidateClosure {

    <T> void action(String attribute, T value);
}
