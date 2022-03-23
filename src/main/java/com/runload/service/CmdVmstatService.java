package com.runload.service;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.alibaba.fastjson.JSONObject;
import com.runload.mapper.CmdVmstatMapper;
import com.runload.pojo.VmstatData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CmdVmstatService {
    @Resource
    private CmdVmstatMapper cmdVmstatMapper;

    private String dateName;

    //获取数据并存入数据库
    public JSONObject order(String host,String user,String pass,String cmd){
        boolean cmdVmstat = false;
        try {
            List<String> commvm = commvm(host, user, pass,cmd);
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            Calendar calendar = Calendar.getInstance();
            dateName = df.format(calendar.getTime());

            for (int k = 0; k < commvm.size(); k++) {
                StringTokenizer piddata = new StringTokenizer(commvm.get(k));
                int j = piddata.countTokens();
                List<Object> data = new ArrayList<>();

                for (int i = 0; i < j; i++) {
                    if (piddata !=null){
                        Object nextElement = piddata.nextElement();
                        data.add(nextElement);
                    }
                }
                //System.out.println(data);
                VmstatData pushpojo = Pushpojo(data);
                if (k>1){
                    cmdVmstat = cmdVmstatMapper.vmstat(pushpojo,dateName);
                }
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }

        JSONObject point = point(dateName, host, user, pass);

        point.put("vid",dateName);
        return point;
    }

    //连接服务器并返回数据
    public List<String> commvm(String host,String user,String pass,String cmd) {

        System.out.println("命令："+cmd);
        List<String> lists = null;
        try {
            Connection conn = new Connection(host);
            conn.connect();

            boolean isAuthenticated = conn.authenticateWithPassword(user, pass);

            if (isAuthenticated == false) {
                System.out.println("SSH Login Authentication failed.");
            } else {
                Session sess = conn.openSession();
                //sess.execCommand("cd ..&&ls");//执行多条命令
                sess.execCommand(cmd);
                InputStream is = new StreamGobbler(sess.getStdout());

                BufferedReader brStat = new BufferedReader(new InputStreamReader(is));
                String line = null;

                StringBuffer buffer = new StringBuffer();
                lists = new ArrayList<>();
                int i = 0;
                while ((line = brStat.readLine()) != null) {
                    buffer.append(line + "\n");
                    System.out.println(line);
                    lists.add(i, line);
                    i++;
                }
                sess.close();
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return lists;
    }

    //分析vmstat结果
    public JSONObject point(String vid, String host, String user, String pass){
        JSONObject allmap = new JSONObject();
        JSONObject cpuimplement = new JSONObject();

        Map<String, String> result = result(vid);
//        result((k,v)->{
//            if (k.equals("us")){
//                nid.putAll(cpuimplement(host, user, pass));
//            }
//        });

        for (Map.Entry<String,String> entry : result.entrySet()){
            String key = entry.getKey();
            if (key.equals("us")){
                cpuimplement = cpuimplement(host, user, pass);
            }
        }
        allmap.putAll(result);
        allmap.putAll(cpuimplement);
        return allmap;
    }

    //获取vmstat指标过高的参数
    public Map<String, String> result(String vid){

        Field[] declaredFields = VmstatData.class.getDeclaredFields();
        Map<String, String> map = new HashMap<>();

        for (Field field : declaredFields){
            StringBuilder sb = new StringBuilder();
            sb.append(field.getName());
            String s = sb.toString();
            Map<String, String> analysisvm = analysisvm(vid, s);
            map.putAll(analysisvm);
        }

        return map;
    }


    //从数据库中分析数据
    public Map<String, String> analysisvm(String vid, String field){
        String fi = null;
        double vm = 0;
        Properties prop = null;

        try {
            prop = PropertiesLoaderUtils.loadProperties(new ClassPathResource("cmdvm.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int fie = Integer.valueOf( prop.getProperty(field));

        List<Map<String, String>> selectvmstat = cmdVmstatMapper.selectvmstat(vid);
//        for(Map<String ,String> map : selectvmstat){
//
//            for (String r:map.keySet()){
//                System.out.println(map.get(r));
//            }
//        }

        List<String> data = new ArrayList<>();
        for(int i=0;i<selectvmstat.size();i++){
            String res = selectvmstat.get(i).get(field);
            data.add(res);
        }

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i)!=null){
                if (data.get(i).matches("\\d+")&&Integer.valueOf(data.get(i))>fie){
                    vm++;
                }
            }
        }

        VmstatData VmstatData = new VmstatData();

        try {
            Field fiel = VmstatData.getClass().getDeclaredField(field);
            fi = String.valueOf(fiel.get(VmstatData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double zo = data.size();
        double threshold = 0.5;
        Map<String,String> map = new HashMap<>();
        if (vm/zo>=threshold){
            map.put(field,fi+"过高，总次数："+zo+"出现次数："+vm);
        }
        return map;
    }

    //cpu过高时分析
    public JSONObject cpuimplement(String host,String user,String pass){
        //获取pid
        List<String> commvm = commvm(host, user, pass, "jps");
        List<Object> data = new ArrayList<>();
        String pid = null;
        for (int i = 0; i < commvm.size(); i++) {
            StringTokenizer tokenizer = new StringTokenizer(commvm.get(i));
            int j = tokenizer.countTokens();
            for (int k = 0; k < j; k++) {
                if (tokenizer !=null){
                    Object nextElement = tokenizer.nextElement();
                    data.add(nextElement);
                }
            }
        }
        for (int q = 0; q < data.size(); q++) {
            if (data.get(q).equals("jar")){
                pid=data.get(q-1).toString();
            }
        }

        //通过进程pid列出线程tid
        String th = "ps -L -o pcpu,tid p "+pid;
        //String th = "ps -L -o pcpu,tid p 16313";
        List<String> thread = commvm(host, user, pass, th);
        String[][] list = new String[thread.size()][2];
        for (int i = 0; i < thread.size(); i++) {
            StringTokenizer izer = new StringTokenizer(thread.get(i));
            int i1 = izer.countTokens();

            for (int k = 0; k <i1 ; k++) {
                if (izer !=null){
                    Object nextElement = izer.nextElement();
                    list[i][k] = nextElement.toString();
                }
            }
        }
        //获取到tid
        int largest = Largest(list);
        String tid = list[largest][1];
        //获取nid
        String nid = null;
        JSONObject id = new JSONObject();
        if (largest>0){
            nid = intToHex(Integer.valueOf(tid));
            String threaddump = "jstack -l "+pid+" | grep -20 "+nid;
            List<String> commvm1 = commvm(host, user, pass, threaddump);

            id.put("pid", pid);
            id.put("tid",tid);
            id.put("nid",nid);
            if (commvm1.size()>0){
                id.put("threaddump",commvm1.toString().replace(",","\n"));
            }else {
                id.put("threaddump",threaddump);
            }
        }else {
            id.put("message","获取数据错误，请重新请求接口！");
        }

        return id;
    }

    //反射机制，将数据写入到实体类中
    private static VmstatData Pushpojo(List<Object> listnr){
        VmstatData vo=new VmstatData();
        Field[] declaredFields = VmstatData.class.getDeclaredFields();
        for(int a=0;a<listnr.size();a++){
            try {
                //list取值赋到vo里
                declaredFields[a].setAccessible(true);
                declaredFields[a].set(vo,listnr.get(a));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return vo;
    }

    //获取最大值
    public static int Largest(String[][] arr) {
        int row = 0;
        double maxValue = 0;

        //遍历数组，获取元素中的最大值及其定位
        for (int i = 1; i < arr.length; i++) {
                if (Double.valueOf(arr[i][0]) > maxValue) {
                    row = i;
                    maxValue = Double.valueOf(arr[i][0]);
                }
        }
        return row;
    }

    //将十进制转为16进制
    private static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }
}
