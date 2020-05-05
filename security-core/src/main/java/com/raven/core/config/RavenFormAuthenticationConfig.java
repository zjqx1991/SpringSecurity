package com.raven.core.config;

import com.raven.core.constants.RavenSecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * form表单配置
 */
@Component
public class RavenFormAuthenticationConfig {

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler);
    }

}
