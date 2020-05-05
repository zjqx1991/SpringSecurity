package com.raven.core.validate.service.impl;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.enums.RavenValidateCodeType;
import com.raven.core.validate.exception.RavenValidateCodeException;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenValidateCodeGenerator;
import com.raven.core.validate.service.IRavenValidateCodeProcessorService;
import com.raven.core.validate.service.IRavenValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

/**
 * 验证码处理器
 */
public abstract class abstractRavenValidateCodeProcessorService<T extends RavenValidateCode> implements IRavenValidateCodeProcessorService {

    /**
     * 收集系统中所有的 {@link IRavenValidateCodeGenerator} 接口的实现。
     * <p>
     * 这是Spring开发的常见技巧，叫做定向查找（定向搜索）
     * <p>
     * Spring启动时，会查找容器中所有的 IValidateCodeGeneratorService 接口的实现，并把Bean的名字作为key，放到map中
     * 在我们这个系统中，IValidateCodeGeneratorService 接口有两个实现，
     * 一个是 DefaultValidateImageCodeGeneratorServiceImpl，
     * 一个是 DefaultValidateSmsCodeGeneratorServiceImpl，
     * 系统启动完成后，这个map中就会有2个bean，key分别是bean的名字
     * <p>
     * 生成验证码的时候，会根据请求的不同（有一个type值区分是获取 短信验证码 还是 图片验证码），来获取短信验证码的生成器或者图形验证码的生成器
     */
    @Autowired
    private Map<String, IRavenValidateCodeGenerator> validateCodeGeneratorsMap;
    @Autowired
    private IRavenValidateCodeRepository validateCodeRepository;

    // 需要验证的url
    private Map<String, RavenValidateCodeType> urlMap;


    @Override
    public void create(ServletWebRequest request) throws Exception {
        // 生成验证码
        T validateCode = generate(request);
        // 放到session
        save(request, validateCode);
        // 发送
        send(request, validateCode);
    }

    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 校验验证码
     *
     * @throws Exception
     */
    @Override
    public void validate(ServletWebRequest request, Map<String, RavenValidateCodeType> urlMap) {
        this.urlMap = urlMap;
        // 获取验证类型
        RavenValidateCodeType codeType = getProcessorType(request);

        T codeInSession = (T) validateCodeRepository.get(request, codeType);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    codeType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new RavenValidateCodeException("获取验证码的值失败");
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new RavenValidateCodeException("验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new RavenValidateCodeException("验证码不存在");
        }

        if (codeInSession.isExpried()) {
            validateCodeRepository.remove(request, codeType);
            throw new RavenValidateCodeException("验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new RavenValidateCodeException("验证码不匹配");
        }

        validateCodeRepository.remove(request, codeType);

    }


    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    private T generate(ServletWebRequest request) {
        String type = getProcessorType(request).toString().toLowerCase();
        IRavenValidateCodeGenerator validateCodeGenerator = validateCodeGeneratorsMap.get(type + RavenSecurityConstants.VALIDATE_CODE_GENERATOR_KEY_SUFFIX);
        return (T) validateCodeGenerator.generator(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        RavenValidateCode code = new RavenValidateCode(validateCode.getCode(), validateCode.getExpireIn());
        validateCodeRepository.save(request, code, getProcessorType(request));
    }

    /**
     * 根据请求的url获取校验码的类型
     */
    private RavenValidateCodeType getProcessorType(ServletWebRequest request) {
        String requestURI = request.getRequest().getRequestURI();
        Map<String, RavenValidateCodeType> urlMap = this.urlMap;
        if (urlMap == null) {
            String type = StringUtils.substringAfter(requestURI, RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX);
            return RavenValidateCodeType.valueOf(type.toUpperCase());
        }
        RavenValidateCodeType codeType = urlMap.get(requestURI);
        if (codeType == null) {
            String type = StringUtils.substringAfter(requestURI, RavenSecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX);
            return RavenValidateCodeType.valueOf(type.toUpperCase());
        }
        return codeType;
    }

}
