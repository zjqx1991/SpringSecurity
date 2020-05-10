/**
 *
 */
package com.raven.browser.config;

import com.raven.browser.logout.BrowserLogoutSuccessHandler;
import com.raven.browser.session.RavenExpiredSessionStrategy;
import com.raven.browser.session.RavenInvalidSessionStrategy;
import com.raven.core.properties.RavenSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全
 * 模块默认的配置。
 *
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private RavenSecurityProperties securityProperties;

	/**
	 * session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		RavenInvalidSessionStrategy invalidSession = new RavenInvalidSessionStrategy(this.securityProperties);
		return invalidSession;
	}

	/**
	 * 并发登录导致前一个session失效时的处理策略配置
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		RavenExpiredSessionStrategy sessionStrategy = new RavenExpiredSessionStrategy(this.securityProperties);
		return sessionStrategy;
	}

	/**
	 * 退出时的处理策略配置
	 *
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(LogoutSuccessHandler.class)
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new BrowserLogoutSuccessHandler(this.securityProperties);
	}

}
