package com.raven.demo.service.impl;


import com.raven.core.authorize.IRavenAuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 自定义权限
 */
@Component
public class DemoAuthorizeConfigProvider implements IRavenAuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/index")
                .hasRole("ADMIN12");
    }
}
