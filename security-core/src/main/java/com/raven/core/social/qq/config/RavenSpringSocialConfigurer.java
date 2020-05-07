package com.raven.core.social.qq.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 社交配置
 * 配置社交拦截器(SocialAuthenticationFilter)拦截的url
 */
public class RavenSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public RavenSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(this.filterProcessesUrl);
        return (T) filter;
    }
}
