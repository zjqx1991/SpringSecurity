package com.raven.core.validate.filter;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.exception.RavenValidateCodeException;
import com.raven.core.validate.processor.RavenValidateCodeProcessor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 验证码过滤器
 * OncePerRequestFilter：确保验证码只执行一次
 * InitializingBean：在初始化完成在执行
 */
@Component("ravenValidateCodeFilter")
public class RavenValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private RavenSecurityProperties securityProperties;
    @Autowired
    private RavenValidateCodeProcessor validateCodeProcessor;

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 存放所有需要校验验证码的url
     */
    private Map<String, RavenValidateCodeType> urlMap = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        // 加载需要使用图片验证
        this.fetchImageValidateURL();
        // 加载需要使用手机短信验证
        this.fetchMobileCodeValidateURL();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取该请求是用 图片 / 短信 校验
        RavenValidateCodeType validateType = this.getValidateCodeType(request);

        if (validateType != null) {
            try {
                this.validateCodeProcessor.findValidateCodeProcessor(validateType).validate(new ServletWebRequest(request, response), this.urlMap);
            }
            catch (RavenValidateCodeException e) {
                this.failureHandler.onAuthenticationFailure(request, response, new RavenValidateCodeException(e.getMessage()));
                return;
            }
        }

        // 继续放行
        filterChain.doFilter(request, response);
    }


    /**
     * 需要使用图形验证码校验的url
     */
    private void fetchImageValidateURL() {
        List validateUrls = this.securityProperties.getValidate().getImage().getValidateUrls();
        this.urlMap.put(RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM, RavenValidateCodeType.IMAGE);
        this.addUrlToMap(validateUrls, RavenValidateCodeType.IMAGE);
    }

    /**
     * 需要使用手机短信验证码校验的url
     */
    private void fetchMobileCodeValidateURL() {
        List validateUrls = this.securityProperties.getValidate().getMobile().getValidateUrls();
        this.urlMap.put(RavenSecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_MOBILE, RavenValidateCodeType.SMS);
        this.addUrlToMap(validateUrls, RavenValidateCodeType.SMS);
    }

    /**
     * 把配置的需要验证的url添加到map中
     * @param urls
     * @param type
     */
    private void addUrlToMap(List<String> urls, RavenValidateCodeType type) {
        urls.stream().forEach(url -> {
            this.urlMap.put(url, type);
        });
    }

    /**
     * 获取校验码的类型，如果当前请求不需要校验，则返回null
     * @param request
     * @return
     */
    private RavenValidateCodeType getValidateCodeType(HttpServletRequest request) {
        RavenValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }

}
