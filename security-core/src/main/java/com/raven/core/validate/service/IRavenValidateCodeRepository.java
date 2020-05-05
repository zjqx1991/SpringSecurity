package com.raven.core.validate.service;

import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.validate.pojo.RavenValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 */
public interface IRavenValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, RavenValidateCode code, RavenValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    RavenValidateCode get(ServletWebRequest request, RavenValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, RavenValidateCodeType codeType);
}
