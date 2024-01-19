package hema.web.validation;

import hema.web.validation.contracts.Factory;
import hema.web.validation.contracts.ValidateRule;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.lang.reflect.Proxy;

@Configuration
public class ValidatorConfiguration {

    private final ApplicationContext applicationContext;

    public ValidatorConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    @Lazy
    public Factory validatorFactory() {
        return new ValidatorFactory(applicationContext);
    }

    @Bean
    @Lazy
    public ValidateRule validateRule() {
        return (ValidateRule) Proxy.newProxyInstance(
                ValidateRule.class.getClassLoader(),
                new Class[]{ValidateRule.class},
                new ValidateRuleProxyInvocationHandler()
        );
    }

}
