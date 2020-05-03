package com.raven.demo;


import com.raven.demo.mapper.IDemoUserMapper;
import com.raven.demo.pojo.DemoUserDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest {

    @Autowired
    private IDemoUserMapper userMapper;

    @Test
    public void getUserInfo() {
        DemoUserDetails raven = this.userMapper.fetchUserInfoByUserName("Raven");
        System.out.println(raven);
    }

    @Test
    public void getRoleIdList() {
        List<String> roleIds = this.userMapper.fetchRoleIdsByUserName("Raven");
        System.out.println(roleIds);
    }


    @Test
    public void getRoleCodeList() {
        List<String> codes = this.userMapper.fetchRoleCodesByUserName("Raven");
        System.out.println(codes);
    }

    @Test
    public void getMenuUrlsList() {
        List<String> menuurls = this.userMapper.fetchMenuUrlsByUserName("Raven");
        System.out.println(menuurls);
    }


    @Test
    public void getMenuUrlsListByRoles() {
        List<String> roleIds = this.userMapper.fetchRoleIdsByUserName("Raven");
        List<String> menuurls = this.userMapper.fetchMenuUrlsByRoleIds(roleIds);
        System.out.println(menuurls);
    }




}
