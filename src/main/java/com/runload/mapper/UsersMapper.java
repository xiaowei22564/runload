package com.runload.mapper;

import com.alibaba.fastjson.JSONObject;
import com.runload.pojo.UsersPojo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UsersMapper {

    @Insert("INSERT INTO `member`.`users`(`name`, `phone`, `address`, `age`, `sex`, `regdate`) " +
            "VALUES (#{name}, #{phone}, #{address}, #{age}, #{sex}, #{regdate})")
    public boolean intouser(UsersPojo usersPojo);

    @UpdateProvider(type = UsersMapperlmpl.class ,method = "updateuser")
    public boolean updateuser(UsersPojo usersPojo);

    @SelectProvider(type = UsersMapperlmpl.class ,method = "selectuser")
    public List<JSONObject> selectuser(String name,String phone);

    @Delete("DELETE FROM users WHERE id=#{id}")
    public boolean deleteuser(String id);

    @Select("SELECT name FROM users")
    public List<String> echartsuser();

    @Select("SELECT age FROM users")
    public List<String> echartsdata();

    @Select("SELECT name,age FROM users")
    public List<JSONObject> mapdata();

    @Select("SELECT * FROM project")
    public List<JSONObject> itemdata();

    @Select("SELECT * FROM thing")
    public List<JSONObject> thingdata();

    @Update("UPDATE thing SET STATUS=#{status} WHERE id=#{id}")
    public boolean thingupdate(int status,String id);

    @Insert("INSERT INTO `member`.`thing`(`title`, `status`, `priority`) VALUES (#{title}, 0, '低')")
    public boolean thingaddtitle(String title);

    @Delete("DELETE FROM thing WHERE id=#{id}")
    public boolean thingdel(String id);
}
