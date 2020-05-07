package com.raven.core.social.qq.connect;

import com.raven.core.social.qq.pojo.RavenQQUserInfo;
import com.raven.core.social.qq.service.IRavenQQService;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * QQ返回数据配置
 * 把QQ返回的数据设置给connect
 */
public class RavenQQApiAdapter implements ApiAdapter<IRavenQQService> {
    @Override
    public boolean test(IRavenQQService api) {
        return true;
    }

    @Override
    public void setConnectionValues(IRavenQQService api, ConnectionValues values) {
        RavenQQUserInfo userInfo = api.fetchQQUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(IRavenQQService api) {
        return null;
    }

    @Override
    public void updateStatus(IRavenQQService api, String message) {

    }
}
