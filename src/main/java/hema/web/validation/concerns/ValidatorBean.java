package hema.web.validation.concerns;

import hema.web.inflector.Inflector;
import hema.web.validation.concerns.verifier.DatabasePresenceVerifier;
import hema.web.validation.concerns.translation.Translation;
import hema.web.validation.exception.ValidationException;
import hema.web.validation.message.Str;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

final class ValidatorBean implements Validator, ValidateAttributes, FormatsMessages {

    private ApplicationContext applicationContext;

    private final Translation translator;

    private final Inflector inflector;

    private Map<String, String> fallbackMessage;

    private Map<String, String> replacers;

    private Map<String, Validator.ValidateRulePredicate> extensions;

    private final Map<String, Object> data;

    private final Haystack<Object> customMessages;

    private final Haystack<String> customAttributes;

    private DatabasePresenceVerifier verifier = null;

    private Map<String, List<String>> implicitAttributes = null;

    private Object currenRule = null;

    /**
     * The initial rules provided.
     */
    private Map<String, Object[]> initialRules;

    /**
     * The rules to be applied to the data.
     */
    private Map<String, Object[]> rules;

    private Set<ValidateHookConsumer> after;

    private final String dotPlaceholder;

    private MessageBag messageBag = null;

    private boolean stopOnFirstFailure = false;

    private Set<String> implicitRules = Set.of(
            "Accepted",
            "Required",
            "String",
            "Url",
            "Integer",
            "Numeric",
            "Json",
            "Map",
            "Array",
            "Uppercase",
            "Lowercase",
            "Email",
            "Phone",
            "Date",
            "Bool",
            "IdCard",
            "BankCard");

    private Set<String> dependentRules = Set.of("Same", "Confirmed", "After", "Before");

    private final String[] sizeRules = {"max", "min", "between"};

    private Set<String> numericRule = Set.of("Integer", "Numeric", "Decimal");

    private final String[] excludeRules = {"Exclude", "ExcludeIf", "ExcludeUnless", "ExcludeWith", "ExcludeWithout"};

    ValidatorBean(Translation translator, Inflector inflector, Map<String, Object> data, Map<String, Object[]> rules,
                  Haystack<Object> messages, Haystack<String> attributes) {

        this.dotPlaceholder = Str.random(16);

        this.translator = translator;
        this.inflector = inflector;
        this.data = parseData(data);
        this.initialRules = rules;
        this.customMessages = messages;
        this.customAttributes = attributes;

        this.setRules(rules);
    }

    private boolean isImplicit(String rule) {
        return implicitRules.contains(rule);
    }

    private boolean hasRule(String needle, String[] haystack) {

        for (String rule : haystack) {
            if (rule.equals(needle)) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseData(Map<String, Object> data) {

        Map<String, Object> newData = new HashMap<>(data);

        data.forEach((key, value) -> {

            if (value instanceof Map<?, ?>) {
                value = parseData((Map<String, Object>) value);
            }

            key = replacePlaceholderInString(key);

            newData.put(key, value);
        });

        return newData;
    }

    /**
     * Set the validation rules.
     *
     * @param rules Custom rules.
     */
    private void setRules(Map<String, Object[]> rules) {

        Map<String, Object[]> newRules = new HashMap<>(rules);

        rules.forEach((key, value) -> {
            newRules.put(key.replace("\\.", dotPlaceholder), value);
        });

        this.initialRules = newRules;

        this.rules.clear();

        addRules(rules);

    }

    private void addRules(Map<String, Object[]> rules) {

    }

    private void validateAttribute(String attribute, Object rule) {

        currenRule = rule;

        final Object[] ruleParsers = ValidationRuleParser.parse(rule);

        String validateRule = ruleParsers[0].toString();

        if (validateRule.isBlank()) {
            return;
        }

        String[] parameters = (String[]) ruleParsers[1];

        if (dependentRules.contains(validateRule)) {

            parameters = replaceDotInParameters(parameters);

            List<String> keys = getExplicitKeys(attribute);

            if (!keys.isEmpty()) {
                parameters = replaceAsterisksInParameters(parameters, keys);
            }
        }

        Object value = getValue(attribute);

    }

    private String[] replaceDotInParameters(String[] parameters) {
        return Arrays.stream(parameters)
                .map(field -> field.replace("\\.", dotPlaceholder))
                .toArray(String[]::new);
    }

    private List<String> getExplicitKeys(String attribute) {

        attribute = getPrimaryAttribute(attribute);

        String pattern = Str.regexQuote(attribute, "#").replace("\\*", "([^\\.]+)");

        Matcher matcher = Pattern.compile(pattern).matcher(attribute);

        List<String> matches = new ArrayList<>();

        while (matcher.find()) {
            IntStream.range(0, matcher.groupCount()).forEach(idx -> matches.add(matcher.group(idx + 1)));
        }

        if (!matches.isEmpty()) {
            return matches;
        }

        return new ArrayList<>();
    }

    private String getPrimaryAttribute(String attribute) {

        Set<String> attributes = implicitAttributes.keySet();

        for (String unparsed : attributes) {

            if (implicitAttributes.get(unparsed).contains(attribute)) {
                return unparsed;
            }

        }

        return attribute;
    }

    private String[] replaceAsterisksInParameters(String[] parameters, List<String> keys) {

        return Arrays.stream(parameters)
                .map(field -> keys.stream().reduce(field, (acc, replace) -> acc.replaceFirst("\\*", replace)))
                .toArray(String[]::new);
    }

    private boolean isValidatable(String rule, String attribute, Object value) {

        if (hasRule(rule, excludeRules)) {
            return true;
        }

        return false;
    }

    private boolean presentOrRuleIsImplicit(String rule, String attribute, Object value) {

        if (validateString(value) && ((String) value).isBlank()) {
            return isImplicit(rule);
        }

        return validatePresent(attribute) || isImplicit(rule);
    }

    @Override
    public Map<String, Object> validated() throws ValidationException {
        return null;
    }

    @Override
    public Map<String, String[]> rules() {
        return null;
    }

    @Override
    public boolean fails() {
        return !passes();
    }

    @Override
    public String[] field() {
        return new String[0];
    }

    @Override
    public MessageBag errors() {
        return messageBag;
    }

    @Override
    public void after(ValidateHookConsumer consumer) {

        if (Objects.isNull(this.after)) {
            this.after = new HashSet<>();
        }

        consumer.accept(this);

        this.after.add(consumer);
    }

    public boolean passes() {
        messageBag = applicationContext.getBean(MessageBag.class);

        if (stopOnFirstFailure && !messageBag.isEmpty()) {
            return false;
        }

        rules();

        // Here we will spin through all of the "after" hook.
        after.forEach(consumer -> consumer.accept(this));

        return messageBag.isEmpty();
    }

    @Override
    public Object getValue(String attribute) {
        return data.get(attribute);
    }

    @Override
    public boolean validatePresent(String attribute) {
        return data.containsKey(attribute);
    }

    public String getMessage(String attribute, String rule) {

        String attributeWithPlaceholders = attribute;

        attribute = replacePlaceholderInString(attribute);

        String inlineMessage = getFromLocalArray(attribute, rule, customMessages);

        if (!inlineMessage.isEmpty()) {
            return inlineMessage;
        }

        String lowerRule = inflector.snake(rule, "_");

        String customKey = STR."validation.custom.\{attribute}.\{lowerRule}";

        String customMessage = getCustomMessageFromTranslator(
                hasRule(rule, sizeRules)
                        ? new String[]{STR."\{customKey}.\{getAttributeType(attribute)}", customKey}
                        : new String[]{customKey}
        );

        if (!customMessage.equals(customKey)) {
            return customMessage;
        } else if (hasRule(rule, sizeRules)) {
            return getSizeMessage(attributeWithPlaceholders, rule);
        }

        String translatorKey = STR."validation.\{lowerRule}";

        if (!translatorKey.equals(translator.get(translatorKey, "en"))) {
            return translator.get(translatorKey, "en");
        }

        String message = getFromLocalArray(attribute, lowerRule, customMessages);

        return message.isEmpty() ? translatorKey : message;
    }

    private String getSizeMessage(String attribute, String rule) {

        String lowerRule = inflector.snake(rule, "_");

        String translatorKey = STR."validation.\{lowerRule}.\{getAttributeType(attribute)}";

        return translator.get(translatorKey, "en");
    }

    private String getCustomMessageFromTranslator(String[] keys) {
        return "";
    }

    private String getAttributeType(String attribute) {
        return numericRule.contains(attribute) ? "numeric" : "string";
    }

    @Override
    public String replacePlaceholderInString(String attribute) {
        return attribute.replace(dotPlaceholder, ".")
                .replace("__asterisk__", "*");
    }

    public void addFailure(String attribute, String rule) {

        if (Objects.isNull(messageBag)) {
            passes();
        }

        String attributeWithPlaceholders = attribute;

        attribute = replacePlaceholderInString(attribute);


    }

    private String[] getRules(String attribute, String[] rules) {
        return new String[0];
    }

    @Override
    public void shouldBeNumeric(String attribute, String rule) {
        if (validateNumeric(String.valueOf(getValue(attribute)))) {
            numericRule.add(rule);
        }
    }

    public Validator stopOnFirstFailure(boolean stopOnFirstFailure) {
        this.stopOnFirstFailure = stopOnFirstFailure;
        return this;
    }

    void setFallbackMessage(Map<String, String> fallbackMessage) {
        this.fallbackMessage = fallbackMessage;
    }

    void setApplication(ApplicationContext application) {
        this.applicationContext = application;
    }

    void setPresenceVerifier(DatabasePresenceVerifier verifier) {
        this.verifier = verifier;
    }

    void addImplicitExtensions(Map<String, Validator.ValidateRulePredicate> implicitRules) {
        addExtensions(implicitRules);
        this.implicitRules.addAll(implicitRules.keySet());
    }

    void addDependentExtensions(Map<String, Validator.ValidateRulePredicate> dependentRules) {
        addExtensions(dependentRules);
        this.dependentRules.addAll(dependentRules.keySet());
    }

    void addExtensions(Map<String, Validator.ValidateRulePredicate> extensions) {
        if (Objects.nonNull(extensions)) {
            this.extensions.putAll(extensions);
        }
    }

    void addReplacers(Map<String, String> replacers) {
        if (Objects.nonNull(replacers)) {
            this.replacers.putAll(replacers);
        }
    }
}
