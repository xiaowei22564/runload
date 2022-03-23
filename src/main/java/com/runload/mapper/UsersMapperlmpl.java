package com.runload.mapper;

import com.runload.pojo.UsersPojo;
import org.apache.ibatis.jdbc.SQL;

public class UsersMapperlmpl {

    public String updateuser(UsersPojo usersPojo) {
        return new SQL() {
            {
                UPDATE("users");
                if (usersPojo.getName() != null) {
                    SET("name=#{name}");
                }
                if (usersPojo.getAddress() != null) {
                    SET("address=#{address}");
                }
                if (usersPojo.getAge()!= 0) {
                    SET("age=#{age}");
                }
                if (usersPojo.getPhone() != null) {
                    SET("phone=#{phone}");
                }
                if (usersPojo.getSex() != null) {
                    SET("sex=#{sex}");
                }
                if (usersPojo.getRegdate() != null) {
                    SET("regdate=#{regdate}");
                }
                WHERE ("id=#{id}");
            }
        }.toString();
    }

    public String selectuser(final String name,String phone){
        return new SQL() {
            {
                SELECT("*");
                FROM("users");
                if (phone != null){
                    WHERE("phone=#{phone}");
                }if (name != null){
                WHERE("name=#{name}");
            }
            }
        }.toString();
    }
}
