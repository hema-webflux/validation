package hema.web.validation.concerns;

import hema.web.validation.contracts.Factory;
import hema.web.validation.contracts.MessageBag;
import hema.web.validation.contracts.ValidateRule;
import hema.web.validation.translation.TranslationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.lang.reflect.Proxy;
import java.util.HashMap;

@Configuration
@Import({TranslationConfiguration.class})
public class ValidatorConfiguration {

    private final ApplicationContext context;

    public ValidatorConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    @Lazy
    public Factory validatorFactory() {
        return new ValidatorFactory(context, new HashMap<>());
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public ValidateRule validateRule() {
        return (ValidateRule) Proxy.newProxyInstance(
                ValidateRule.class.getClassLoader(),
                new Class[]{ValidateRule.class},
                new ValidateRuleProxy(new HashMap<>())
        );
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public MessageBag messageBag() {
        return new ValidateMessageBag(new HashMap<>());
    }
}
