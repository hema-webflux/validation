package hema.web.validation.concerns;

import hema.web.validation.concerns.verifier.DatabasePresenceVerifier;
import hema.web.validation.concerns.translation.Translation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.lang.reflect.Proxy;
import java.util.HashMap;

@Configuration
public class ValidatorConfiguration {

    private final ApplicationContext context;

    public ValidatorConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    @Lazy
    public Factory validatorFactory() {

        ValidatorFactory factory = new ValidatorFactory(context, context.getBean(Translation.class));

        factory.setPresenceVerifier(context.getBean(DatabasePresenceVerifier.class));

        return factory;
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
