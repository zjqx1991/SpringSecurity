package com.raven.browser.service.impl;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenValidateCodeRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;

@Service
public class BrowserSessionValidateCodeRepository implements IRavenValidateCodeRepository {


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();


//    @Override
    public void save(ServletWebRequest request, RavenValidateCode code, RavenValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(validateCodeType), code);
    }

//    @Override
    public RavenValidateCode get(ServletWebRequest request, RavenValidateCodeType validateCodeType) {
        return (RavenValidateCode) sessionStrategy.getAttribute(request, getSessionKey(validateCodeType));
    }

//    @Override
    public void remove(ServletWebRequest request, RavenValidateCodeType codeType) {
        sessionStrategy.removeAttribute(request, getSessionKey(codeType));
    }

    /**
     * 构建验证码放入session时的key
     *
     * @return
     */
    private String getSessionKey(RavenValidateCodeType validateCodeType) {
        return RavenSecurityConstants.SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

}
