package com.raven.core.social.qq.config;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.properties.social.qq.RavenQQProperties;
import com.raven.core.social.qq.connect.RavenQQConnectionFactory;
import com.raven.core.social.spring.SocialAutoConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * QQ配置
 */
@Configuration
@ConditionalOnProperty(prefix = "raven.security.social.qq", name = "appId")
public class RavenQQConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private RavenSecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        RavenQQProperties qqConfig = this.securityProperties.getSocial().getQq();
        return new RavenQQConnectionFactory(
                qqConfig.getProviderId(),
                qqConfig.getAppId(),
                qqConfig.getAppSecret()
                );
    }
}
