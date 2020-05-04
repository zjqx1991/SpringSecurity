package com.raven.browser.controller;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.response.RavenR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当需要身份认证时，跳转到这里
 */
@RestController
public class BrowserSecurityController {


    private Logger logger = LoggerFactory.getLogger(getClass());
    // 获取session缓存
    private RequestCache requestCache = new HttpSessionRequestCache();
    // 跳转工具类
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

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
    @RequestMapping("/authentication/require")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public RavenR requireAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String targetUrl = savedRequest.getRedirectUrl();
            String loginPage = this.securityProperties.getBrowser().getLoginPage();
            logger.info("引发跳转的请求是:" + targetUrl);
            logger.info("引发跳转的请求是:" + loginPage);
            if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
                redirectStrategy.sendRedirect(request, response, loginPage);
            }
        }
        return new RavenR("访问的服务需要身份认证，请引导用户到登录页");
    }
}
