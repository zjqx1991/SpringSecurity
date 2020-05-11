package com.raven.core.social.service;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * SocialAuthenticationFilter 后处理器
 */
public interface IRavenSocialAuthenticationFilterPostProcessor {

    void processor(SocialAuthenticationFilter socialAuthenticationFilter);
}
