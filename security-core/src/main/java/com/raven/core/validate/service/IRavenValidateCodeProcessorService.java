package com.raven.core.validate.service;

import com.raven.core.enums.RavenValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 */
public interface IRavenValidateCodeProcessorService {

    /**
     * 创建校验码
     * @param request  ServletWebRequest封装了 request和response
     */
    void create(ServletWebRequest request) throws Exception;


    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest, Map<String, RavenValidateCodeType> urlMap);

}
