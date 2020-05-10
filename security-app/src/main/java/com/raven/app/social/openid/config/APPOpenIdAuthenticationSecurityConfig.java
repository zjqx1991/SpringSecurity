package com.raven.app.social.openid.config;

import com.raven.app.social.openid.APPOpenIdAuthenticationFilter;
import com.raven.app.social.openid.APPOpenIdAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 校验openId的配置类---》将校验规则等配置到spring-security过滤器链中
 */
@Component
public class APPOpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository jdbcUsersConnectionRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //UsersConnectionRepository usersConnectionRepository = new NrscJdbcUsersConnectionRepository();
        APPOpenIdAuthenticationFilter OpenIdAuthenticationFilter = new APPOpenIdAuthenticationFilter();
        OpenIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        OpenIdAuthenticationFilter.setAuthenticationSuccessHandler(this.successHandler);
        OpenIdAuthenticationFilter.setAuthenticationFailureHandler(this.failureHandler);

        APPOpenIdAuthenticationProvider OpenIdAuthenticationProvider = new APPOpenIdAuthenticationProvider();
        OpenIdAuthenticationProvider.setUserDetailsService(this.userDetailsService);
        OpenIdAuthenticationProvider.setUsersConnectionRepository(this.jdbcUsersConnectionRepository);

        http.authenticationProvider(OpenIdAuthenticationProvider)
                .addFilterAfter(OpenIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
