package hema.web.validation.proxy;

import hema.web.validation.contracts.ValidateRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Proxy;

@Configuration
public class ValidateRuleProxyConfiguration {

    @Bean
    @Lazy
    @Scope("prototype")
    public ValidateRule validateRule() {
        return (ValidateRule) Proxy.newProxyInstance(
                ValidateRule.class.getClassLoader(),
                new Class[]{ValidateRule.class},
                new ValidateRuleProxy()
        );
    }
}
