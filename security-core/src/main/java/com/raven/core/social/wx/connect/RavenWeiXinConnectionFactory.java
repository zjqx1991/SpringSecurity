/**
 *
 */
package com.raven.core.social.wx.connect;

import com.raven.core.social.wx.api.IRavenWeiXin;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * 微信连接工厂
 *
 * @author zhailiang
 *
 */
public class RavenWeiXinConnectionFactory extends OAuth2ConnectionFactory<IRavenWeiXin> {

	/**
	 * @param appId
	 * @param appSecret
	 */
	public RavenWeiXinConnectionFactory(String providerId, String appId, String appSecret) {
		super(providerId, new RavenWeiXinServiceProvider(appId, appSecret), new RavenWeiXinAdapter());
	}

	/**
	 * 由于微信的openId是和accessToken一起返回的，所以在这里直接根据accessToken设置providerUserId即可，不用像QQ那样通过QQAdapter来获取
	 */
	@Override
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if(accessGrant instanceof RavenWeiXinAccessGrant) {
			return ((RavenWeiXinAccessGrant)accessGrant).getOpenId();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.oauth2.AccessGrant)
	 */
	public Connection<IRavenWeiXin> createConnection(AccessGrant accessGrant) {
		return new OAuth2Connection<IRavenWeiXin>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
				accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
	}

	/* (non-Javadoc)
	 * @see org.springframework.social.connect.support.OAuth2ConnectionFactory#createConnection(org.springframework.social.connect.ConnectionData)
	 */
	public Connection<IRavenWeiXin> createConnection(ConnectionData data) {
		return new OAuth2Connection<IRavenWeiXin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
	}

	private ApiAdapter<IRavenWeiXin> getApiAdapter(String providerUserId) {
		return new RavenWeiXinAdapter(providerUserId);
	}

	private OAuth2ServiceProvider<IRavenWeiXin> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<IRavenWeiXin>) getServiceProvider();
	}


}
