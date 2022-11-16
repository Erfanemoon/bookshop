package erfan.codes.bookshop.general.common.global.config;

import erfan.codes.bookshop.aspect.SpringValidatorRegistry;
import erfan.codes.bookshop.general.common.global.MyValidator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public MyValidator myValidator() {
        return new MyValidator();
    }

    @Bean
    public SpringValidatorRegistry springValidatorRegistry() {
        return new SpringValidatorRegistry();
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetObject(springValidatorRegistry());
        methodInvokingFactoryBean.setTargetMethod("addValidator");
        methodInvokingFactoryBean.setArguments(myValidator());
        return methodInvokingFactoryBean;
    }


//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        JedisConnectionFactory factory = new JedisConnectionFactory();
//        return factory;
//    }

}
