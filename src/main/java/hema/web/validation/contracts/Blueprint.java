package hema.web.validation.contracts;

public interface Blueprint {

    Blueprint bind(String attribute, String value);

    Blueprint bind(String attribute, BlueprintClosure closure);

    boolean isClosure(String attribute);

    boolean has(String key);

}