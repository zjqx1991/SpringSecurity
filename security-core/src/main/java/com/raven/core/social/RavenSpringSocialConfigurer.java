package com.raven.core.social;

import com.raven.core.social.service.IRavenSocialAuthenticationFilterPostProcessor;
import lombok.Setter;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * 社交配置
 * 配置社交拦截器(SocialAuthenticationFilter)拦截的url
 */
@Setter
public class RavenSpringSocialConfigurer extends SpringSocialConfigurer {

    private String filterProcessesUrl;
    private IRavenSocialAuthenticationFilterPostProcessor postProcessor;

    public RavenSpringSocialConfigurer(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);
        filter.setFilterProcessesUrl(this.filterProcessesUrl);
        if (this.postProcessor != null) {
            this.postProcessor.processor(filter);
        }
        return (T) filter;
    }
}
