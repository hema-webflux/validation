package hema.web.validation.contracts;

import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.Set;

public interface ValidateRule {

    ValidateRule field(@NonNull String value);

    ValidateRule required();

    ValidateRule requiredUnless(String... values);

    ValidateRule requiredWith(String... values);

    ValidateRule requiredWithAll(String... values);

    ValidateRule requiredWithout(String... values);

    ValidateRule requiredMapKeys(String... values);

    ValidateRule string();

    ValidateRule url();

    ValidateRule size(@NonNull int value);

    ValidateRule uppercase();

    ValidateRule nullable();

    ValidateRule accepted();

    ValidateRule acceptedIf(@NonNull String value);

    ValidateRule unique(@NonNull String table, @NonNull String column);

    ValidateRule exists(@NonNull String table, @NonNull String column);

    ValidateRule same(@NonNull String field);

    ValidateRule regex(@NonNull String pattern);

    ValidateRule integer();

    ValidateRule numeric();

    ValidateRule email();

    ValidateRule phone();

    ValidateRule identityNumber();

    ValidateRule bankCardNumber();

    ValidateRule min(@NonNull int value);

    ValidateRule max(@NonNull int value);

    ValidateRule confirmed(@NonNull String field);

    ValidateRule json();

    ValidateRule map();

    ValidateRule inMap();

    ValidateRule array();

    ValidateRule inArray();

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

    <T> ValidateRule isEnum(@NonNull Class<T> enumClass);

    Map<String, Set<String>> rules();
}