package hema.web.validation.contracts;

public interface ValidateRule {

    ValidateRule target(String field);

    ValidateRule required();

    ValidateRule requiredUnless(String... values);

    ValidateRule requiredWith(String... values);

    ValidateRule requiredWithAll(String... values);

    ValidateRule requiredWithout(String... values);

    ValidateRule requiredMapKeys(String... values);

    ValidateRule string();

    ValidateRule url();

    ValidateRule size();

    ValidateRule uppercase();

    ValidateRule nullable();

    ValidateRule accepted();

    ValidateRule acceptedIf(String value);

    ValidateRule unique(String table, String column);

    ValidateRule exists(String table, String column);

    ValidateRule same(String field);

    ValidateRule regex(String pattern);

    ValidateRule integer();

    ValidateRule numeric();

    ValidateRule email();

    ValidateRule phone();

    ValidateRule min();

    ValidateRule max();

    ValidateRule confirmed();

    ValidateRule json();

    ValidateRule map();

    ValidateRule inMap();

    ValidateRule distinct();

    ValidateRule distinctStrict();

    ValidateRule distinctIgnoreCase();

    ValidateRule startWith(String... values);

    ValidateRule endWith(String... values);

    ValidateRule doesntStartWith(String... values);

    ValidateRule doesntEndWith(String... values);

    ValidateRule lowercase();

    ValidateRule mimes(String... values);

    ValidateRule missing();

    ValidateRule missingIf(String... values);

    ValidateRule bool();

    ValidateRule date();

    ValidateRule dateEquals(String date);

    ValidateRule different(String field);

    ValidateRule digits(int length);

    ValidateRule digitsBetween(int min, int max);

    ValidateRule in(String... values);

    ValidateRule before(String value);

    ValidateRule after(String value);

    ValidateRule between(int min, int max);

    ValidateRule decimal(int min, int max);

    <T> ValidateRule assertEnum(Class<T> enumClass);
}
