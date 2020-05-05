package com.raven.browser;

import com.raven.browser.authentication.BrowserAuthenticationFailureHandler;
import com.raven.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.filter.RavenMobileValidateCodeFilter;
import com.raven.core.validate.filter.RavenValidateCodeFilter;
import com.raven.core.validate.mobile.RavenMobileCodeAuthenticationFilter;
import com.raven.core.validate.mobile.config.RavenMobileCodeAuthenticationSecurityConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


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
    @Autowired
    private RavenValidateCodeFilter validateCodeFilter;
    @Autowired
    private RavenMobileCodeAuthenticationSecurityConfig mobileCodeConfig;
    @Autowired
    private HikariDataSource hikariDataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(this.hikariDataSource);
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置登录界面
        String loginPage = this.securityProperties.getBrowser().getLoginPage();
        int tokenTime = this.securityProperties.getBrowser().getTokenTime();

        RavenMobileValidateCodeFilter mobileFilter = new RavenMobileValidateCodeFilter();
        mobileFilter.setFailureHandler(this.browserAuthenticationFailureHandler);
        mobileFilter.setSecurityProperties(this.securityProperties);
        mobileFilter.afterPropertiesSet();


        http.csrf().disable()
                .apply(this.mobileCodeConfig)
                .and()
                .addFilterBefore(this.validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(mobileFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")    // 当需要身份认证时，跳转到这里
                .loginProcessingUrl("/authentication/form") // 默认处理的/login，自定义登录界面需要指定请求路径
                .successHandler(this.browserAuthenticationSuccessHandler)
                .failureHandler(this.browserAuthenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(tokenTime)
                .userDetailsService(this.userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        loginPage,
                        "/code/*"
                ).permitAll()
                .anyRequest()
                .authenticated();
    }
}
