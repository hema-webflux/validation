package hema.web.validation.support;

sealed abstract class RuleStore implements ValidateRuleProxy.Store
        permits RuleStore.FixedParameterRuleStore,
        RuleStore.NormalRuleStore,
        RuleStore.SingleParameterRuleStore,
        RuleStore.UnlimitedParameterRuleStore {

    @Override
    public ValidateRuleProxy.Store push(String key, Object value) {
        return null;
    }

    abstract protected void store(String key,Object value);

    private non-sealed static class NormalRuleStore extends RuleStore {

        @Override
        protected void store(String key, Object value) {

        }
    }

    private non-sealed static class SingleParameterRuleStore extends RuleStore {

        @Override
        protected void store(String key, Object value) {

        }
    }

    private non-sealed static class FixedParameterRuleStore extends RuleStore {

        @Override
        protected void store(String key, Object value) {

        }
    }

    private non-sealed static class UnlimitedParameterRuleStore extends RuleStore {

        @Override
        protected void store(String key, Object value) {

        }
    }

}
