package com.raven.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web端security配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .formLogin()
                .loginPage("/bw-login.html")    // 自定义登录界面
                .loginProcessingUrl("/authentication/form") // 默认处理的/login，自定义登录界面需要指定请求路径
                .and()
                .authorizeRequests()
                .antMatchers("/bw-login.html").permitAll()
                .anyRequest()
                .authenticated();
    }
}
