package hema.web.validation.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final public class ValidateMethods {
    private final String[] implicitRuleMethods = {"required", "requiredUnless", "requiredWith", "requiredWithout", "accepted", "acceptedIf"};

    private final String[] wordRuleMethods = {"uppercase", "lowercase", "startWith", "endWith", "doesntStartWith", "doesntEndWith"};

    private final String[] typeRuleMethods = {"string", "bool", "json", "url", "date"};

    private final String[] authRuleMethod = {"email", "phone", "identityNumber", "bankCardNumber"};

    private final String[] regexRuleMethods = {"regex", "notRegex"};

    private final String[] elementRuleMethods = {"in", "inArray", "inMap", "distinct", "map", "array",};

    private final String[] classRuleMethods = {"isEnum"};

    private final String[] dependentRuleMethods = {"same", "before", "after", "confirmed", "dateEquals", "unique", "exists", "different"};

    private final String[] sizeRuleMethods = {"size", "between", "min", "max",};

    private final String[] numericRuleMethods = {"digits", "digitsBetween", "decimal", "integer", "numeric",};

    private final Map<String, Set<String>> allowMethods = new HashMap<>();

    ValidateMethods() {
        initialMethodMaps();
    }

    private void initialMethodMaps() {

        Field[] privateFields = getClass().getDeclaredFields();

        for (Field field : privateFields) {

            try {
                Object methods = field.get(this);

                if (methods.getClass().isArray()) {
                    allowMethods.put(field.getName(), Set.of((String[]) methods));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isCallSerial(Method method) {
        return method.getName().equals("rules");
    }

    public boolean isNullable(Method method) {
        return method.getName().equals("nullable");
    }

    public boolean isSetAttribute(Method method) {
        return method.getName().equals("field");
    }
}
