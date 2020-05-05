package com.raven.core.validate.processor;


import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.validate.exception.RavenValidateCodeException;
import com.raven.core.validate.service.IRavenValidateCodeProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RavenValidateCodeProcessor {

    /**
     * 依赖搜索
     *
     * Spring启动时，会查找容器中所有的 IRavenValidateCodeProcessorService 接口的实现，
     * 并把Bean的名字作为key，放到map中
     */
    @Autowired
    private Map<String, IRavenValidateCodeProcessorService> validateCodeProcessorMap;

    /**
     * @param type
     * @return
     */
    public IRavenValidateCodeProcessorService findValidateCodeProcessor(RavenValidateCodeType type) {

        String name = type.toString().toLowerCase() + RavenSecurityConstants.VALIDATE_CODE_PROCESSOR_KEY_SUFFIX;
        IRavenValidateCodeProcessorService validateCodeProcessor = this.validateCodeProcessorMap.get(name);
        if (validateCodeProcessor == null) {
            throw new RavenValidateCodeException("验证码处理器 " + name + " 不存在");
        }
        return validateCodeProcessor;
    }

}
