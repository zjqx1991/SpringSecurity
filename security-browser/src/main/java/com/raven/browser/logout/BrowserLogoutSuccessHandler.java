package com.raven.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.response.RavenR;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description：退出成功处理器
 */
@Slf4j
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 退出登陆url
     *      可以在yml或properties文件里通过raven.security.browser.signOutUrl 进行指定
     *      我指定的默认值为"/" --- 因为如果不指定一个默认的url时，配置授权那一块会报错
     */
    private RavenSecurityProperties securityProperties;

    private ObjectMapper objectMapper = new ObjectMapper();
    //private ObjectMapper objectMapper = new ObjectMapper();
    public BrowserLogoutSuccessHandler(RavenSecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("退出成功");
        String signOutSuccessUrl = this.securityProperties.getBrowser().getSignOutUrl();

        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new RavenR("退出成功")));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }

    }
}
