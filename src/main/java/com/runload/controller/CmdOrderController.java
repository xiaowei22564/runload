package com.runload.controller;

import com.alibaba.fastjson.JSONObject;
import com.runload.mapper.CmdVmstatMapper;
import com.runload.service.CmdVmstatService;
import com.runload.unit.Result;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CmdOrderController {
    @Resource
    private CmdVmstatService vmstatService;

    @Resource
    private CmdVmstatMapper cmdVmstatMapper;

    @RequestMapping(value = "vmstat",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Result cmdvmstat(@RequestBody JSONObject object){
        Object host = object.get("host");
        Object user = object.get("user");
        Object pass = object.get("pass");
        Object cmd = object.get("cmd");

        Result result = new Result(200,"ok");

        if (object.size() == 4){
            JSONObject commvm = vmstatService.order(host.toString(), user.toString(), pass.toString(), cmd.toString());

            if (commvm.size()>0){
                result.setData(commvm);
                result.setMessage("获取"+cmd+"命令数据成功！");
            }else {
                result.setStatus(400);
                result.setMessage("连接超时！");
            }
        }else {
            result.setStatus(500);
            result.setMessage("message:连接信息错误！");
        }
        return result;
    }

    //post请求
    public void postrest(String host,String user,String pass,String vid){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8011/analysisvm";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("host",host);
        map.add("user",user);
        map.add("pass",pass);
        map.add("vid",vid);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());

    }

    //get请求
    public void getrest(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("city","武汉");
        String forObject = restTemplate.getForObject("http://wthrcdn.etouch.cn/weather_mini", String.class, map);
        System.out.println(forObject);
    }

    @RequestMapping(value = "analysisvm")
    public Result analysisvm(@RequestBody JSONObject object) {
        Object host = object.get("host");
        Object user = object.get("user");
        Object pass = object.get("pass");
        Object vid = object.get("vid");

        Result result = new Result(200,"ok");

        JSONObject point = vmstatService.point(vid.toString(), host.toString(), user.toString(), pass.toString());

        if (point !=null){
            result.setData(point);
        }else {
            result.setStatus(400);
            result.setMessage("message:未发现指标异常！");
        }

        return result;
    }

    @RequestMapping(value = "selctvm")
    public JSONObject vmdata(@RequestBody JSONObject object){
        int num = 0;
        String vid = null;

        String pagenum1 = object.getString("pagenum");
        int pagenum = Integer.valueOf(pagenum1);
        if (pagenum>1){
            num = (pagenum-1)*20;
        }

        if (object.containsKey("vid")&&object.get("vid")!=""){
            vid = object.getString("vid");
            num=0;
        }

        int vmcount = cmdVmstatMapper.vmcount();
        List<JSONObject> selectvmstat = cmdVmstatMapper.selectvm(vid,num);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", vmcount);
        jsonObject.put("data",selectvmstat);
        return jsonObject;
    }

    @RequestMapping(value = "point")
    public JSONObject point(){
        Map<String,String> id = new HashMap<>();
        Map<String,String> m = new HashMap<>();
        id.put("1","1");
        id.put("2","2");
        id.put("3","3");

        m.put("4","4");
        m.put("5","5");
        m.put("6","6");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","小伟");
        jsonObject.put("age","10");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name","黄伟");
        jsonObject1.put("age","15");

        List<JSONObject> list = new ArrayList();
        list.add(jsonObject1);

        jsonObject.put("dada",list);
        jsonObject.put("map",id);
        jsonObject.put("json",jsonObject1);

        return jsonObject;
    }

}
