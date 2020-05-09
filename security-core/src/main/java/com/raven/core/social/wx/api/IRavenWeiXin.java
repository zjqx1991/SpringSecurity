/**
 *
 */
package com.raven.core.social.wx.api;

import com.raven.core.social.wx.pojo.RavenWeiXinUserInfo;

/**
 * 微信API调用接口
 *
 * @author zhailiang
 *
 */
public interface IRavenWeiXin {

	/**
	 * 微信用户信息
	 * @param openId
	 * @return
	 */
	RavenWeiXinUserInfo getUserInfo(String openId);
}
