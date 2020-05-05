package com.raven.core.validate.service.impl;

import com.raven.core.validate.pojo.RavenImageCode;
import com.raven.core.validate.pojo.RavenValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;


public class DefaultRavenImageCodeProcessor extends abstractRavenValidateCodeProcessorService {

    @Override
    protected void send(ServletWebRequest request, RavenValidateCode validateCode) throws Exception {
        RavenImageCode imageCode = (RavenImageCode) validateCode;
        ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
