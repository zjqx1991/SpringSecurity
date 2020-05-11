package com.raven.core.authorize.impl;

import com.raven.core.authorize.IRavenAuthorizeConfigManager;
import com.raven.core.authorize.IRavenAuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 默认的授权配置管理器
 *
 */
@Component
public class RavenAuthorizeConfigManager implements IRavenAuthorizeConfigManager {

    @Autowired
    private Set<IRavenAuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (IRavenAuthorizeConfigProvider provider : this.authorizeConfigProviders) {
            provider.config(config);
        }
        config.anyRequest().authenticated();
    }
}
