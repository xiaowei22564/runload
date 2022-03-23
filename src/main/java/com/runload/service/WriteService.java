package com.runload.service;

import com.alibaba.fastjson.JSONObject;
import com.runload.mapper.CmdVmstatMapper;
import com.runload.pojo.FilePojo;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WriteService {
    @Resource
    private CmdVmstatMapper cmdVmstatMapper;

    public void file() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook("D:/数据1.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        int rownum = sheet.getLastRowNum();
        int cellNum = sheet.getRow(0).getLastCellNum();

        List celldata = new ArrayList<>();

        for (int i = 1; i < rownum; i++) {
            for (int k = 0; k < cellNum; k++) {
                celldata.add(k,String.valueOf(sheet.getRow(i).getCell(k)));
            }

            FilePojo filePojo = filepojo(celldata);
            cmdVmstatMapper.file(filePojo);
            celldata.clear();
        }
    }

    public Map testnamedata(String iteration){
        List<JSONObject> selectfiledata = cmdVmstatMapper.selectfiledata(iteration);
        Map map = new HashMap();
        List list = new ArrayList();
        List list1 = new ArrayList();
        for (int i = 0; i < selectfiledata.size(); i++) {
            list.add(selectfiledata.get(i).getString("name"));
            list1.add(selectfiledata.get(i).getString("data"));
        }
        map.put("name",list);
        map.put("data",list1);
        return map;
    }

    //反射机制，将数据写入到实体类中
    private static FilePojo filepojo(List<Object> listnr){
        FilePojo vo=new FilePojo();
        Field[] declaredFields = FilePojo.class.getDeclaredFields();
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
}
