package com.raven.app.service.impl;

import com.raven.app.authentication.AppAuthenticationSuccessHandler;
import com.raven.core.social.service.IRavenSocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 在APP情况下，让授权走自己定义的成功处理器
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements IRavenSocialAuthenticationFilterPostProcessor {

    @Autowired
    private AppAuthenticationSuccessHandler successHandler;

    @Override
    public void processor(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(this.successHandler);
    }
}
