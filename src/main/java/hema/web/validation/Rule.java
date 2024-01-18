package hema.web.validation;

public class Rule {

    private final static String[] defaultRules = {
            "required", "nullable", "unique", "same", "integer", "numeric",
            "email", "phone", "min", "max", "confirmed", "json", "map",
            "inMap", "bool", "date", "in", "before", "after", "between",
            "decimal", "assert_enum", "required_if", "exists", "regex",
            "accepted", "accepted_if", "date_equals", "different", "digits",
            "digits_between", "distinct", "distinct_strict", "distinct_ignore_case",
            "start_with", "end_with", "doesnt_start_with", "doesnt_end_with",
            "lowercase", "mimes", "missing", "missing_if", "required_unless",
            "required_with", "required_with_all", "required_without",
            "require_map_keys", "url", "uppercase", "size"
    };

}
