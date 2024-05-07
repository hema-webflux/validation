package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.translation.Translation;

import java.util.Map;

public interface Factory {

    /**
     * Create a new Validator instance.
     *
     * @param data         Input data.
     * @param validateRule Rule proxy.
     * @param messages     Custom rule validate Message sources.
     * @param attributes   Custom rule attribute name.
     * @return Return new Validator instance.
     */
    Validator make(Map<String, Object> data, ValidateRule validateRule, Haystack<Object> messages, Haystack<String> attributes);

    /**
     * Register a custom validator extension.
     *
     * @param rule    custom rule name.
     * @param closure custom rule.
     * @param message rule message.
     */
    void extend(String rule, Validator.ValidateRulePredicate closure, String message);

    /**
     * Register a custom implicit validator extension.
     * Verify the property even when it is empty.
     * <p>
     * Example:
     * <code>
     * public void test(Factory factory) {
     * // Required if NOT
     * factory.extendImplicit("language",(attribute,value,parameters) -> {
     * return value.equals("javascript");
     * },"language must be in: java,ruby,php");
     * }
     * </code>
     *
     * @param rule    rule name.
     * @param closure validate callback.
     * @param message validate error message.
     */
    void extendImplicit(String rule, Validator.ValidateRulePredicate closure, String message);

    /**
     * Register a custom dependent validator extension.
     * Allows you to add user-defined rules that depend on other fields.
     * <p></p>
     * e: requiredIf:language,java
     *
     * @param rule    rule name.
     * @param closure validate callback.
     * @param message validate error message.
     */
    void extendDependent(String rule, Validator.ValidateRulePredicate closure, String message);

    @FunctionalInterface
    interface Resolver {

        Validator apply(
                Translation translation, Inflector inflector, Map<String, Object> data, ValidateRule rule,
                Haystack<Object> messages, Haystack<String> attributes
        );

    }
}
