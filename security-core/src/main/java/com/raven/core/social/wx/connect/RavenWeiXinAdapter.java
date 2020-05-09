/**
 *
 */
package com.raven.core.social.wx.connect;

import com.raven.core.social.wx.api.IRavenWeiXin;
import com.raven.core.social.wx.pojo.RavenWeiXinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * 微信 api适配器，将微信 api的数据模型转为spring social的标准模型。
 *
 *
 * @author zhailiang
 *
 */
public class RavenWeiXinAdapter implements ApiAdapter<IRavenWeiXin> {

	private String openId;

	public RavenWeiXinAdapter() {}

	public RavenWeiXinAdapter(String openId){
		this.openId = openId;
	}

	/**
	 * @param api
	 * @return
	 */
	@Override
	public boolean test(IRavenWeiXin api) {
		return true;
	}

	/**
	 * @param api
	 * @param values
	 */
	@Override
	public void setConnectionValues(IRavenWeiXin api, ConnectionValues values) {
		RavenWeiXinUserInfo profile = api.getUserInfo(openId);
		values.setProviderUserId(profile.getOpenid());
		values.setDisplayName(profile.getNickname());
		values.setImageUrl(profile.getHeadimgurl());
	}

	/**
	 * @param api
	 * @return
	 */
	@Override
	public UserProfile fetchUserProfile(IRavenWeiXin api) {
		return null;
	}

	/**
	 * @param api
	 * @param message
	 */
	@Override
	public void updateStatus(IRavenWeiXin api, String message) {
		//do nothing
	}

}
