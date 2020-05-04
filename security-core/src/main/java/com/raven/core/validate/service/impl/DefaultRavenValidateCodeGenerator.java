package com.raven.core.validate.service.impl;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.pojo.ImageCode;
import com.raven.core.validate.service.IRavenValidateCodeGenerator;
import lombok.Setter;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 默认的图形验证码生成器
 */
@Setter
public class DefaultRavenValidateCodeGenerator implements IRavenValidateCodeGenerator {

    private RavenSecurityProperties securityProperties;

    @Override
    public ImageCode generator(ServletWebRequest request) {

        // 首先从请求参数中获取验证码的宽度，如果没有则使用配置的值
        // 这里是实现了验证码参数的三级可配：请求级>应用级>默认配置
        int current_width = ServletRequestUtils.getIntParameter(request.getRequest(), "raven_width", securityProperties.getValidate().getImage().getWidth());
        int current_height = ServletRequestUtils.getIntParameter(request.getRequest(), "raven_height", securityProperties.getValidate().getImage().getHeight());
        int current_length = this.securityProperties.getValidate().getImage().getLength();
        int expireIn = this.securityProperties.getValidate().getImage().getExpireIn();
        int width = current_width;
        int height = current_height;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < current_length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, expireIn);
    }

    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
