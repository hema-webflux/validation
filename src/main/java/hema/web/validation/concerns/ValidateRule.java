package hema.web.validation.concerns;

import org.springframework.lang.NonNull;

import java.util.Map;

public interface ValidateRule {

    ValidateRule field(@NonNull String field);

    ValidateRule required();

    ValidateRule requiredUnless(String... values);

    ValidateRule requiredWith(@NonNull String field);

    ValidateRule requiredWithAll(String... fields);

    ValidateRule requiredWithout(@NonNull String field);

    ValidateRule requiredWithoutAll(String... fields);

    ValidateRule requiredMapKeys(String... values);

    ValidateRule string();

    ValidateRule url();

    ValidateRule integer();

    ValidateRule numeric();

    ValidateRule json();

    ValidateRule map();

    ValidateRule array();

    ValidateRule size(@NonNull int value);

    ValidateRule uppercase();

    ValidateRule lowercase();

    ValidateRule nullable();

    ValidateRule accepted();

    ValidateRule acceptedIf(@NonNull String value);

    ValidateRule declined();

    ValidateRule unique(@NonNull String table, @NonNull String column);

    ValidateRule exists(@NonNull String table, @NonNull String column);

    ValidateRule same(@NonNull String field);

    ValidateRule regex(@NonNull String pattern);

    ValidateRule notRegex(@NonNull String pattern);

    ValidateRule email();

    ValidateRule phone();

    ValidateRule idCard();

    ValidateRule bankCard();

    ValidateRule min(@NonNull int value);

    ValidateRule max(@NonNull int value);

    ValidateRule confirmed(@NonNull String field);

    ValidateRule inMap();

    ValidateRule inArray();

    ValidateRule distinct();

    ValidateRule distinctStrict();

    ValidateRule distinctIgnoreCase();

    ValidateRule startWith(String... values);

    ValidateRule endWith(String... values);

    ValidateRule doesntStartWith(String... values);

    ValidateRule doesntEndWith(String... values);

    ValidateRule mimes(String... values);

    ValidateRule missing();

    ValidateRule missingIf(String... values);

    ValidateRule in(String... values);

    ValidateRule bool();

    ValidateRule date();

    ValidateRule dateEquals(@NonNull String date);

    ValidateRule different(@NonNull String field);

    ValidateRule digits(@NonNull int length);

    ValidateRule digitsBetween(@NonNull int min, @NonNull int max);

    ValidateRule before(@NonNull String value);

    ValidateRule after(@NonNull String value);

    ValidateRule between(@NonNull int min, @NonNull int max);

    ValidateRule decimal(@NonNull int min, @NonNull int max);

    <T> ValidateRule creationEnum(@NonNull Class<T> kind);

    ValidateRule rawRule(String rules);

    ValidateRule rawRule(String[] rules);

    Map<String, Object[]> rules();

}
