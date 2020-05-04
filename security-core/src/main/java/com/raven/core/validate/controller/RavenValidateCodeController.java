package com.raven.core.validate.controller;

import com.raven.core.constants.RavenSecurityConstants;
import com.raven.core.properties.RavenSecurityProperties;
import com.raven.core.validate.pojo.ImageCode;
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
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@RestController
public class RavenValidateCodeController {


    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private RavenSecurityProperties securityProperties;

    @GetMapping("/code/image")
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 生成图片
        ImageCode imageCode = generator(new ServletWebRequest(request, response));
        // 保存
        sessionStrategy.setAttribute(new ServletWebRequest(request, response), RavenSecurityConstants.SESSION_KEY_PREFIX, imageCode);
        // 发送
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }


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
