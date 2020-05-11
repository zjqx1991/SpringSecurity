package com.raven.demo.web.controller;

import com.raven.core.properties.RavenSecurityProperties;
import com.raven.demo.mapper.IDemoUserMapper;
import com.raven.demo.pojo.DemoUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
public class DemoController {

    @Autowired
    private IDemoUserMapper userMapper;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
//    @Autowired
//    private AppSignUpUtils appSignUpUtils;
    @Autowired
    private RavenSecurityProperties securityProperties;


    @PostMapping("/user/regist")
    public void register(DemoUserDetails userDetails, HttpServletRequest request) {
        DemoUserDetails details = this.userMapper.fetchUserInfoByUserName(userDetails.getUsername());
        System.out.println("userDetails = " + userDetails);
        this.providerSignInUtils.doPostSignUp("Raven", new ServletWebRequest(request));
//        this.appSignUpUtils.doPostSignUp(new ServletWebRequest(request), "Raven");

    }

    @GetMapping("/me")
    public Object getCurrentUser1() {
        //方式1，直接从SecurityContextHolder中拿到Authentication对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    // 登录
//    @PostMapping("/login")
//    public String index(String username,String password) {
//        return "index";
//    }

    // 登录成功之后的首页
    @GetMapping("/index")
    public String index(Authentication auth, HttpServletRequest request) throws Exception {
//        String signingKey = this.securityProperties.getOauth2().getSigningKey();
//        String header = request.getHeader("Authorization");
//        String token = StringUtils.substringAfter(header, "Bearer ");
//        Claims claims = Jwts.parser().setSigningKey(signingKey.getBytes("UTF-8")).parseClaimsJws(token).getBody();
//
//        String company = (String) claims.get("company");
//        log.info(company);
        return "index";
    }

    // 日志管理
    @GetMapping("/syslog")
    public String showOrder() {
        return "syslog";
    }

    // 用户管理
    @GetMapping("/sysuser")
    public String addOrder() {
        return "sysuser";
    }

    // 具体业务一
    @GetMapping("/biz1")
    public String updateOrder() {
        return "biz1";
    }

    // 具体业务二
    @GetMapping("/biz2")
    public String deleteOrder() {
        return "biz2";
    }


}
