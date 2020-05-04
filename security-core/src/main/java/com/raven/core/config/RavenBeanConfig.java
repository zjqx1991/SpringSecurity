package com.raven.core.config;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.service.IRavenValidateCodeGenerator;
import com.raven.core.validate.service.impl.DefaultRavenValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Configuration
public class RavenBeanConfig {

    @Autowired
    private RavenSecurityProperties securityProperties;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 由于IRavenValidateCodeGenerator这个接口是为了让外界实现，所以不能在它默认的实现类
     * DefaultRavenValidateCodeGenerator上直接使用@Component，防止会造成2个Bean，
     * 所以需要使用@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
     * 来判断Bean imageValidateCodeGenerator是否存在，如果存在就不会执行下面的Bean
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    IRavenValidateCodeGenerator imageValidateCodeGenerator() {
        DefaultRavenValidateCodeGenerator generator = new DefaultRavenValidateCodeGenerator();
        generator.setSecurityProperties(this.securityProperties);
        return generator;
    }
}
