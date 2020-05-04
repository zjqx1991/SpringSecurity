package com.raven.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raven.core.enums.RavenLoginType;
import com.raven.core.properties.RavenSecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RavenSecurityProperties securityProperties;


    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        logger.info("自定义失败处理器");

        if (RavenLoginType.JSON.equals(this.securityProperties.getBrowser().getLoginType())) {

            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(this.objectMapper.writeValueAsString(exception));
        }
        else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }

}
