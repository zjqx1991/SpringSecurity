/**
 *
 */
package com.raven.core.social.wx.config;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.properties.social.weixin.RavenWeiXinProperties;
import com.raven.core.social.spring.SocialAutoConfigurerAdapter;
import com.raven.core.social.view.ImoocConnectView;
import com.raven.core.social.wx.connect.RavenWeiXinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * 微信登录配置
 *
 * @author zhailiang
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "raven.security.social.wx", name = "appId")
public class RavenWeixinAutoConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private RavenSecurityProperties securityProperties;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter
	 * #createConnectionFactory()
	 */
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		RavenWeiXinProperties weixinConfig = securityProperties.getSocial().getWx();
		return new RavenWeiXinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
				weixinConfig.getAppSecret());
	}

	@Bean({"connect/weixinConnect", "connect/weixinConnected"})
	@ConditionalOnMissingBean(name = "weixinConnectedView")
	public View weixinConnectedView() {
		return new ImoocConnectView();
	}

}
