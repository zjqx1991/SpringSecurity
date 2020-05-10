package com.raven.browser;

import com.raven.browser.authentication.BrowserAuthenticationFailureHandler;
import com.raven.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.raven.core.config.RavenFormAuthenticationConfig;
import com.raven.core.config.RavenValidateCodeSecurityConfig;
import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.mobile.config.RavenMobileCodeAuthenticationSecurityConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;


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
    // 社交配置
    @Autowired
    private SpringSocialConfigurer socialConfigurer;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformation;
    @Autowired
    private InvalidSessionStrategy invalidSession;
    /**退出成功处理器*/
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

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
        String signUpUrl = this.securityProperties.getBrowser().getSignUpUrl();
        //session失效默认的跳转地址
        String sessionInvalidUrl = this.securityProperties.getBrowser().getSession().getSessionInvalidUrl();
        //推出登录默认的跳转地址
        String signOutUrl = this.securityProperties.getBrowser().getSignOutUrl();

        // 表单配置
        this.formAuthenticationConfig.configure(http);
        // 验证码配置
        http.apply(this.validateCodeSecurityConfig);
        // 短信配置
        http.apply(this.mobileCodeConfig);
        // 社交配置
        http.apply(this.socialConfigurer);
        http.csrf().disable();
        http.rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(tokenTime)
                .userDetailsService(this.userDetailsService)
                .and()
                .sessionManagement()
                .invalidSessionStrategy(this.invalidSession)
                .maximumSessions(this.securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(this.securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                .expiredSessionStrategy(this.sessionInformation)
                .and()
                .and()
                //退出登陆相关的逻辑
                .logout()
                //自定义退出的url---默认的为/logout
                .logoutUrl("/logout")
                //自定义退出成功处理器
                .logoutSuccessHandler(logoutSuccessHandler)
                //自定义退出成功后跳转的url与logoutSuccessHandler互斥
                //.logoutSuccessUrl("/index")
                //指定退出成功后删除的cookie
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers(
                        RavenSecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM,
                        loginPage,
                        RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "*",
                        signUpUrl,
                        sessionInvalidUrl,
                        signOutUrl,
                        "/user/regist"

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
