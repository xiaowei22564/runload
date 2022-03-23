package com.runload.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runload.mapper.UsersMapper;
import com.runload.pojo.UsersPojo;
import com.runload.service.UsersService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UsersController {
    @Resource
    private UsersService usersService;
    @Resource
    private UsersMapper usersMapper;

    @RequestMapping(value = "adduser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String intousers(@RequestBody UsersPojo usersPojo){

        String ret;
        boolean intousers = usersService.intousers(usersPojo);
        if (intousers==true){
            ret="新增成功";
        }else {
            ret="新增失败";
        }
        return ret;
    }

    @RequestMapping(value = "updateuser")
    @ResponseBody
    public boolean updateusers(@RequestBody UsersPojo usersPojo){
        boolean updateusers = usersService.updateusers(usersPojo);
        return updateusers;
    }

    @RequestMapping(value = "selectuser")
    @ResponseBody
    public List<JSONObject> selectuser(@RequestBody JSONObject object){

        String name = replaceBlank(object.getString("name"));
        String phone = replaceBlank(object.getString("phone"));

        if (name.length()==0){
            name = null;
        }
        if (phone.length()==0){
            phone = null;
        }
        List<JSONObject> selectusers = usersMapper.selectuser(name,phone);
        return selectusers;
    }

    @RequestMapping(value = "deleteuser")
    @ResponseBody
    public String deleteuser(String id){
        String message;
        boolean deleteusers = usersService.deleteusers(id);
        if(deleteusers==true){
            message="删除成功！";
        }else {
            message="删除失败！";
        }
        return message;
    }

    @RequestMapping(value = "userdata")
    @ResponseBody
    public Map userdata(){
        Map usermap = new HashMap();
        List<String> echartsuser = usersMapper.echartsuser();
        List<String> echartsdata = usersMapper.echartsdata();
        usermap.put("user",echartsuser);
        usermap.put("data",echartsdata);

        return usermap;
    }

    @RequestMapping(value = "circulardata")
    @ResponseBody
    public Map circulardata(){
        Map usermap = new HashMap();
        List<String> echartsuser = usersMapper.echartsuser();
        List<JSONObject> echartsdata = usersMapper.mapdata();
        List<JSONObject> modifyjson = modifyjson(echartsdata, "age", "value");
        usermap.put("user",echartsuser);
        usermap.put("data",modifyjson);

        return usermap;
    }

    @RequestMapping(value = "itemdata")
    @ResponseBody
    public List<JSONObject> itemdata(){
        List<JSONObject> itemdata = usersMapper.itemdata();

        return itemdata;
    }

    @RequestMapping(value = "thingdata")
    @ResponseBody
    public List<JSONObject> thingdata(){
        List<JSONObject> itemdata = usersMapper.thingdata();

        return itemdata;
    }

    @RequestMapping(value = "thingupdate")
    @ResponseBody
    public String thingupdate(String status,String id){
        String massage = null;
        int stat = 1;

        if (status.equals("true")){
            stat = 0;
        }else {
            stat = 1;
        }

        boolean thingupdate = usersMapper.thingupdate(stat, id);
        if (thingupdate=true){
            massage = "修改成功";
        }else {
            massage = "修改失败";
        }

        return massage;
    }

    @RequestMapping(value = "thingaddtitle")
    @ResponseBody
    public String thingaddtitle(String title){
        String massage = null;
        boolean thingaddtitle = usersMapper.thingaddtitle(title);
        if (thingaddtitle=true){
            massage = "新增成功";
        }else {
            massage = "新增失败";
        }
        return massage;
    }

    @RequestMapping(value = "thingdeltitle")
    @ResponseBody
    public String thingdeltitle(String id){
        String massage = null;
        boolean thingdeltitle = usersMapper.thingdel(id);
        if (thingdeltitle=true){
            massage = "删除成功";
        }else {
            massage = "删除失败";
        }
        return massage;
    }

    public static String replaceBlank(String str) {

        String dest = "";

        if (str != null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;

    }

    //修改json格式的KEY值，Modified需要修改的值，newfield修改后的值
    public static List<JSONObject> modifyjson(List<JSONObject> listjson,String Modified,String newfield){

        List<JSONObject> list = new ArrayList();
        for (JSONObject map: listjson){
            String json = JSON.toJSONString(map);
            // 获取JSON某个key的值
            JSONObject jsonObj = JSON.parseObject(json);
            jsonObj.put(newfield, jsonObj.getString(Modified));
            jsonObj.remove(Modified);
            list.add(jsonObj);
        }

        return list;
    }
}
