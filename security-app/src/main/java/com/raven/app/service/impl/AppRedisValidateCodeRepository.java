package com.raven.app.service.impl;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenValidateCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AppRedisValidateCodeRepository implements IRavenValidateCodeRepository {

    private static final String MOBILE_CODE_KEY_FIX = "mobile_code_key_fix_";

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RavenSecurityProperties securityProperties;

    @Override
    public void save(ServletWebRequest request, RavenValidateCode code, RavenValidateCodeType validateCodeType) {
        String key = this.buildKey(request);
        this.redisTemplate.opsForValue().set(key, code, this.securityProperties.getValidate().getMobile().getExpireIn(), TimeUnit.MINUTES);
    }

    @Override
    public RavenValidateCode get(ServletWebRequest request, RavenValidateCodeType validateCodeType) {
        String key = this.buildKey(request);
        Object code = this.redisTemplate.opsForValue().get(key);

        if (code == null) {
            return null;
        }
        return (RavenValidateCode) code;
    }

    @Override
    public void remove(ServletWebRequest request, RavenValidateCodeType codeType) {
        this.redisTemplate.delete(this.buildKey(request));
    }

    /**
     * 手机验证码存储KEY
     */
    private String buildKey(ServletWebRequest request) {
        HttpServletRequest request1 = request.getRequest();
        String mobile = request1.getParameter(RavenSecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE);
        return MOBILE_CODE_KEY_FIX + mobile;
    }
}
