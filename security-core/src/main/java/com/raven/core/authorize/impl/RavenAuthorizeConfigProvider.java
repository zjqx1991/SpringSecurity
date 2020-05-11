package com.raven.core.authorize.impl;

import com.raven.core.authorize.IRavenAuthorizeConfigProvider;
import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里。
 */
@Component
public class RavenAuthorizeConfigProvider implements IRavenAuthorizeConfigProvider {

    @Autowired
    private RavenSecurityProperties securityProperties;


    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE,
                RavenSecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                this.securityProperties.getBrowser().getLoginPage(),
                this.securityProperties.getBrowser().getSignInPage(),
                this.securityProperties.getBrowser().getSignUpUrl(),
                this.securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                this.securityProperties.getBrowser().getSignOutUrl()
        ).permitAll();

    }

}
