package com.raven.core.validate.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class RavenValidateCode implements Serializable {
    private String code;
    private LocalDateTime expireTime;
    private int expireIn = 10;

    public RavenValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn * 60);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
