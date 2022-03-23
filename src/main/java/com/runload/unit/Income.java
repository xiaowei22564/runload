package com.runload.unit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.math.BigDecimal;

public class Income {
    public static void main(String[] args) {
        test();
    }

    public static void test(){
        File file = new File("D:\\1.txt");
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        JSONObject jSONObject = JSONObject.parseObject(sbf.toString());
        String jin = jSONObject.getString("data");

        JSONObject object = JSONObject.parseObject(jin);
        JSONArray rows = object.getJSONArray("rows");

        BigDecimal zong = new BigDecimal("0");
        for (int i = 0; i < rows.size(); i++) {
            JSONObject jsonObject = rows.getJSONObject(i);
            Object jine = jsonObject.get("订单金额");

            String s = jine.toString();
            BigDecimal bigDecimal = new BigDecimal(s);
            //Integer d = Integer.valueOf(s);
            zong = zong.add(bigDecimal);
        }

        System.out.println(zong);
    }
}
