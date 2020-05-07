package com.raven.core.social.qq.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.core.social.qq.pojo.RavenQQUserInfo;
import com.raven.core.social.qq.service.IRavenQQService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;


/**
 * 获取第三方平台（QQ）用户的信息
 */
public class RavenQQServiceImpl extends AbstractOAuth2ApiBinding implements IRavenQQService {


    /**
     * 获取QQ的 openId 请求url
     */
    private final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * 获取QQ的 userInfo 请求url
     */
    private final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private String appId;
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 在构造器中获取 openId
     */
    public RavenQQServiceImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;

        // 发送请求来获取openId
        String openIdUrl = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(openIdUrl, String.class);
        logger.info(result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public RavenQQUserInfo fetchQQUserInfo() {
        String userInfoUrl = String.format(URL_GET_USERINFO, this.appId, this.openId);
        String result = getRestTemplate().getForObject(userInfoUrl, String.class);

        try {
            RavenQQUserInfo userInfo = JSON.parseObject(result, RavenQQUserInfo.class);
            userInfo.setOpenId(this.openId);
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
