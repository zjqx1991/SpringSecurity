/**
 *
 */
package com.raven.core.social.wx.connect;

import com.raven.core.social.wx.api.IRavenWeiXin;
import com.raven.core.social.wx.api.RavenWeiXinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 *
 * 微信的OAuth2流程处理器的提供器，供spring social的connect体系调用
 *
 * @author zhailiang
 *
 */
public class RavenWeiXinServiceProvider extends AbstractOAuth2ServiceProvider<IRavenWeiXin> {

	/**
	 * 微信获取授权码的url
	 */
	private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
	/**
	 * 微信获取accessToken的url
	 */
	private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/**
	 * @param appId
	 * @param appSecret
	 */
	public RavenWeiXinServiceProvider(String appId, String appSecret) {
		super(new RavenWeiXinOAuth2Template(appId, appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
	}


	/* (non-Javadoc)
	 * @see org.springframework.social.oauth2.AbstractOAuth2ServiceProvider#getApi(java.lang.String)
	 */
	@Override
	public IRavenWeiXin getApi(String accessToken) {
		return new RavenWeiXinImpl(accessToken);
	}

}
