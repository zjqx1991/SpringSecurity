package com.raven.browser;

import com.raven.browser.authentication.BrowserAuthenticationFailureHandler;
import com.raven.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web端security配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RavenSecurityProperties securityProperties;
    @Autowired
    private BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler;
    @Autowired
    private BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置登录界面
        String loginPage = this.securityProperties.getBrowser().getLoginPage();

        http.csrf().disable()
                .formLogin()
                .loginPage("/authentication/require")    // 当需要身份认证时，跳转到这里
                .loginProcessingUrl("/authentication/form") // 默认处理的/login，自定义登录界面需要指定请求路径
                .successHandler(this.browserAuthenticationSuccessHandler)
                .failureHandler(this.browserAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        loginPage
                ).permitAll()
                .anyRequest()
                .authenticated();
    }
}
