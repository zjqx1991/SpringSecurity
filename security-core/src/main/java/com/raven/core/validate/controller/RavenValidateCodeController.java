package com.raven.core.validate.controller;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.validate.pojo.RavenImageCode;
import com.raven.core.validate.pojo.RavenValidateCode;
import com.raven.core.validate.service.IRavenMobileCodeSendService;
import com.raven.core.validate.service.IRavenMobileValidateCodeGenerator;
import com.raven.core.validate.service.IRavenValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RavenValidateCodeController {


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private IRavenValidateCodeGenerator imageValidateCodeGenerator;
    @Autowired
    private IRavenMobileValidateCodeGenerator mobileValidateCodeGenerator;
    @Autowired
    private IRavenMobileCodeSendService mobileCodeSendService;


    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成图片
        RavenImageCode imageCode = this.imageValidateCodeGenerator.generator(new ServletWebRequest(request, response));
        // 保存
        sessionStrategy.setAttribute(new ServletWebRequest(request, response), RavenSecurityConstants.SESSION_KEY_PREFIX, imageCode);
        // 发送
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createMobileCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取手机号
        String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
        // 生成验证码
        RavenValidateCode validateCode = this.mobileValidateCodeGenerator.generator(new ServletWebRequest(request, response));
        // 保存
        sessionStrategy.setAttribute(new ServletWebRequest(request, response), RavenSecurityConstants.SESSION_KEY_PREFIX, validateCode);
        // 发送
        this.mobileCodeSendService.send(mobile, validateCode.getCode());
    }

}
