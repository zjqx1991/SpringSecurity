package com.raven.core.mobile.config;

import com.raven.core.mobile.RavenMobileCodeAuthenticationFilter;
import com.raven.core.mobile.RavenMobileCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class RavenMobileCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 手机短信登录过滤器
        RavenMobileCodeAuthenticationFilter mobileFilter = new RavenMobileCodeAuthenticationFilter();
        mobileFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        mobileFilter.setAuthenticationSuccessHandler(this.successHandler);
        mobileFilter.setAuthenticationFailureHandler(this.failureHandler);

        //Provider
        RavenMobileCodeAuthenticationProvider mobileProvider = new RavenMobileCodeAuthenticationProvider();
        mobileProvider.setUserDetailsService(this.userDetailsService);

        // 把自定义Provider 和 Filter 加入到SpringSecurity的过滤器链上
        http.authenticationProvider(mobileProvider)
                .addFilterAfter(mobileFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
