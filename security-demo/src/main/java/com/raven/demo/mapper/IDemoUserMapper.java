package com.raven.demo.mapper;

import com.raven.demo.pojo.DemoUserDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IDemoUserMapper {

    /**
     * @param username 用户名称
     * @return 用户信息
     */
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    DemoUserDetails fetchUserInfoByUserName(@Param("username") String username);


    /**
     * @param username 用户名称
     * @return 角色id列表
     */
    @Select("SELECT ur.role_id \n" +
            "FROM t_user_role ur \n" +
            "LEFT JOIN t_user u\n" +
            "ON ur.user_id = u.id\n" +
            "WHERE u.username = #{username}")
    List<String> fetchRoleIdsByUserName(@Param("username") String username);

    /**
     * @param username 用户名称
     * @return 角色code列表
     */
    @Select("SELECT r.role_code\n" +
            "FROM t_role r\n" +
            "LEFT JOIN (t_user_role ur \n" +
            "LEFT JOIN t_user u\n" +
            "ON ur.user_id = u.id)\n" +
            "ON r.id = ur.role_id\n" +
            "WHERE u.username = #{username}")
    List<String> fetchRoleCodesByUserName(@Param("username") String username);

    /**
     * @param username 用户名称
     * @return 权限url列表
     */
    @Select("SELECT m.url\n" +
            "FROM t_menu m\n" +
            "LEFT JOIN (t_role_menu rm\n" +
            "LEFT JOIN (t_user_role ur \n" +
            "LEFT JOIN t_user u\n" +
            "ON ur.user_id = u.id)\n" +
            "ON rm.role_id = ur.role_id)\n" +
            "ON m.id = rm.menu_id\n" +
            "WHERE u.username = #{username}")
    List<String> fetchMenuUrlsByUserName(@Param("username") String username);


    /**
     * @param roleIds 角色id列表
     * @return 权限url列表
     */
    @Select({
            "<script>",
                "SELECT m.url\n" +
                    "FROM t_menu m\n" +
                    "LEFT JOIN t_role_menu rm\n" +
                    "ON m.id = rm.menu_id\n" +
                    "WHERE rm.role_id IN ",
                "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>",
                    "#{roleId}",
                "</foreach>",
            "</script>"
    })
    List<String> fetchMenuUrlsByRoleIds(@Param("roleIds") List<String> roleIds);


}
