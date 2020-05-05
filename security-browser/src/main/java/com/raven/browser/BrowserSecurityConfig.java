package com.raven.browser;

import com.raven.browser.authentication.BrowserAuthenticationFailureHandler;
import com.raven.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.raven.core.config.RavenFormAuthenticationConfig;
import com.raven.core.config.RavenValidateCodeSecurityConfig;
import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.filter.RavenValidateCodeFilter;
import com.raven.core.validate.mobile.config.RavenMobileCodeAuthenticationSecurityConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private HikariDataSource hikariDataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    // form表单配置
    @Autowired
    private RavenFormAuthenticationConfig formAuthenticationConfig;
    // 验证码配置
    @Autowired
    private RavenValidateCodeSecurityConfig validateCodeSecurityConfig;
    // 短信配置
    @Autowired
    private RavenMobileCodeAuthenticationSecurityConfig mobileCodeConfig;

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

        // 表单配置
        this.formAuthenticationConfig.configure(http);
        // 验证码配置
        http.apply(this.validateCodeSecurityConfig);
        // 短信配置
        http.apply(this.mobileCodeConfig);
        http.csrf().disable();
        http.rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(tokenTime)
                .userDetailsService(this.userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers(
                        RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                        loginPage,
                        RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "*"
                ).permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**");
    }
}
