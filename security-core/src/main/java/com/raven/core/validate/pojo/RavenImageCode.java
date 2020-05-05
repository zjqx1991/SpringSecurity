package com.raven.core.validate.pojo;

import lombok.Getter;
import lombok.Setter;
import java.awt.image.BufferedImage;

@Setter
@Getter
public class RavenImageCode extends RavenValidateCode {
    private BufferedImage image;

    public RavenImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }
}
