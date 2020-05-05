package com.raven.core.validate.pojo;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
public class RavenValidateCode {
    private String code;
    private LocalDateTime expireTime;
    private int expireIn = 60;

    public RavenValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
