package hema.web.validation.concerns;

public interface ValidatorAwareRule {

    /**
     * Set current validator.
     *
     * @param validator Validator instance.
     */
    void setValidator(Validator validator);
}
