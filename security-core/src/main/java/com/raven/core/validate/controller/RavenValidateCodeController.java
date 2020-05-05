package com.raven.core.validate.controller;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.validate.service.IRavenValidateCodeProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class RavenValidateCodeController {


    @Autowired
    private Map<String, IRavenValidateCodeProcessorService> validateCodeProcessorServiceMap;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link IRavenValidateCodeProcessorService}接口实现
     * @param request
     * @param response
     * @param type
     */
    @GetMapping("/code/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws Exception {
        validateCodeProcessorServiceMap.get(type + RavenSecurityConstants.VALIDATE_CODE_PROCESSOR_KEY_SUFFIX).create(new ServletWebRequest(request, response));
    }

}
