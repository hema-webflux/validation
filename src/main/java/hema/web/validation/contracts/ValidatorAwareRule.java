package hema.web.validation.contracts;

public interface ValidatorAwareRule {

    /**
     * Set current validator.
     *
     * @param validator Validator instance.
     */
    void setValidator(Validator validator);
}
