package com.raven.core.validate.filter;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.exception.RavenValidateCodeException;
import com.raven.core.validate.pojo.RavenImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 验证码过滤器
 * OncePerRequestFilter：确保验证码只执行一次
 * InitializingBean：在初始化完成在执行
 */
@Component
public class RavenValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private RavenSecurityProperties securityProperties;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private Set<String> validateUrlSet = new HashSet<>();


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        List validateUrls = this.securityProperties.getValidate().getImage().getValidateUrls();
        this.validateUrlSet.addAll(validateUrls);
        this.validateUrlSet.add("/authentication/form");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        boolean action = false;

        for (String url : this.validateUrlSet) {
            if (this.pathMatcher.match(url, request.getRequestURI())) {
                action = true;
            }
        }

        if (action) {
            try {
                validate(new ServletWebRequest(request, response));
            }
            catch (RavenValidateCodeException e) {
                this.failureHandler.onAuthenticationFailure(request, response, new RavenValidateCodeException(e.getMessage()));
                return;
            }
        }

        // 继续放行
        filterChain.doFilter(request, response);
    }

    private void validate(ServletWebRequest request) throws ServletRequestBindingException {

        RavenImageCode imageCode = (RavenImageCode) this.sessionStrategy.getAttribute(request, RavenSecurityConstants.SESSION_KEY_PREFIX);

        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");

        if (StringUtils.isBlank(codeInRequest)) {
            throw new RavenValidateCodeException("验证码的值不能为空");
        }

        if (imageCode == null) {
            throw new RavenValidateCodeException("验证码不存在");
        }

        if (imageCode.isExpried()) {
            this.sessionStrategy.removeAttribute(request, RavenSecurityConstants.SESSION_KEY_PREFIX);
            throw new RavenValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(imageCode.getCode(), codeInRequest)) {
            throw new RavenValidateCodeException("验证码不匹配");
        }

        this.sessionStrategy.removeAttribute(request, RavenSecurityConstants.SESSION_KEY_PREFIX);

    }

}
