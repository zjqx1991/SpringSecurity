package com.raven.browser.controller;

import com.raven.core.pojo.BrowserSocialUserInfo;
import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.response.RavenR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当需要身份认证时，跳转到这里
 */
@RestController
@Slf4j
public class BrowserSecurityController {

    // 获取session缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    // 跳转工具类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private RavenSecurityProperties securityProperties;

    /**
     * 当需要身份认证时，跳转到这里
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(RavenSecurityConstants.DEFAULT_SESSION_INVALID_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public RavenR requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            String loginPage = this.securityProperties.getBrowser().getLoginPage();

            log.info("引发跳转的请求是:" + targetUrl);
            log.info("引发跳转的请求是:" + loginPage);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, loginPage);
            }
        }
        return new RavenR("访问的服务需要身份认证，请引导用户到登录页");
    }


    @GetMapping(RavenSecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public BrowserSocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        BrowserSocialUserInfo userInfo = new BrowserSocialUserInfo();
        Connection<?> connection = this.providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        ConnectionKey connectionKey = connection.getKey();
        userInfo.setProviderId(connectionKey.getProviderId());
        userInfo.setProviderUserId(connectionKey.getProviderUserId());
        userInfo.setNickName(connection.getDisplayName());
        userInfo.setHeadImg(connection.getImageUrl());
        return null;
    }


    @GetMapping("/session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public RavenR invalidSession() {
        return new RavenR("Session 失效");
    }

}
