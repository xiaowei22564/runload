package com.runload.unit;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CommLinux {


    public static void main(String[] args) {
        List<String> commvm = commvm("192.168.203.3", "root", "root", "jps");
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
        System.out.println(pid);
    }

    public static List<String> commvm(String host,String user,String pass,String cmd) {

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

    public static JSONObject test(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","小伟");
        jsonObject.put("age","10");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name","黄伟");
        jsonObject1.put("age","15");

        List<JSONObject> list = new ArrayList();
        list.add(jsonObject1);

        jsonObject.put("dada",list);
        return jsonObject;
    }

//    private static JSch jsch;
//    private static Session session;
//
//    public static void connect(String user, String passwd, String host) throws JSchException {
//        jsch = new JSch();
//        session = jsch.getSession(user, host, 22);
//        session.setPassword(passwd);
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//
//        session.connect();
//    }
//
//    public static void execCmd(String command, String user, String passwd, String host) throws JSchException {
//        connect(user, passwd, host);
//        BufferedReader reader = null;
//        Channel channel = null;
//        try {
//            while (command != null) {
//                channel = session.openChannel("exec");
//                ((ChannelExec) channel).setCommand(command);
//                channel.setInputStream(null);
//                ((ChannelExec) channel).setErrStream(System.err);
//                channel.connect();
////获取流
//                InputStream in = channel.getInputStream();
//                reader = new BufferedReader(new InputStreamReader(in));
//                String buf = null;
//                while ((buf = reader.readLine()) != null) {
//                    System.out.println(buf);//打印返回的结果日志
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSchException e) {
//            e.printStackTrace();
//        } finally {//最后流和连接的关闭
//            try {
//                reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            channel.disconnect();
//            session.disconnect();
//        }
//    }
//
//    public static void main(String[] args) throws JSchException {
//        execCmd("cd /home/java/jdk1.8.0_161/bin&&jstack -l 16313 >test.dump","root","root","192.168.203.3");
//    }

}