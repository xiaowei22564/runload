package com.runload.service;

import com.runload.mapper.UsersMapper;
import com.runload.pojo.UsersPojo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UsersService {

    @Resource
    private UsersMapper usersMapper;

    public boolean intousers(UsersPojo usersPojo){
        boolean intouser = usersMapper.intouser(usersPojo);
        return intouser;
    }

    public boolean updateusers(UsersPojo usersPojo){
        boolean updateuser = usersMapper.updateuser(usersPojo);
        return updateuser;
    }

    public boolean deleteusers(String id){
        boolean deleteuser = usersMapper.deleteuser(id);
        return deleteuser;
    }
}
