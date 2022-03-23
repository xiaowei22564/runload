package com.runload.mapper;

import com.runload.pojo.FilePojo;
import com.runload.pojo.VmstatData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class CmdVmstatMapperlmpl {

    public String vmstat(VmstatData cmdVmstatPojo, @Param("dateName") String dateName){

        SQL sql = new SQL();
        return new SQL(){
            {
                INSERT_INTO("vmstat");
//                INTO_COLUMNS("r","b","swpd","free","buff","cache","si","so","bi","bo","in","cs","us","sy","id","wa","st");
//
//                INTO_VALUES("#{r}","#{b}","#{swpd}","#{free}","#{buff}","#{cache}","#{si}","#{so}","#{bi}","#{bo}",
//                        "#{in}","#{cs}","#{us}","#{sy}","#{id}","#{wa}","#{st}");

                if (dateName.length() > 0){
                    VALUES("vid","#{dateName}");
                }
                if (cmdVmstatPojo.getR() != null){
                    VALUES("r","#{cmdVmstatPojo.r}");
                }
                if (cmdVmstatPojo.getB() != null){
                    VALUES("b","#{cmdVmstatPojo.b}");
                }
                if (cmdVmstatPojo.getSwpd() != null){
                    VALUES("swpd","#{cmdVmstatPojo.swpd}");
                }
                if (cmdVmstatPojo.getFree() != null){
                    VALUES("free","#{cmdVmstatPojo.free}");
                }
                if (cmdVmstatPojo.getBuff() != null){
                    VALUES("buff","#{cmdVmstatPojo.buff}");
                }
                if (cmdVmstatPojo.getCache() != null){
                    VALUES("cache","#{cmdVmstatPojo.cache}");
                }
                if (cmdVmstatPojo.getSi() != null){
                    VALUES("si","#{cmdVmstatPojo.si}");
                }
                if (cmdVmstatPojo.getSo() != null){
                    VALUES("so","#{cmdVmstatPojo.so}");
                }
                if (cmdVmstatPojo.getBi() != null){
                    VALUES("bi","#{cmdVmstatPojo.bi}");
                }
                if (cmdVmstatPojo.getBo() != null){
                    VALUES("bo","#{cmdVmstatPojo.bo}");
                }
                if (cmdVmstatPojo.getIns() != null){
                    VALUES("ins","#{cmdVmstatPojo.ins}");
                }
                if (cmdVmstatPojo.getCs() != null){
                    VALUES("cs","#{cmdVmstatPojo.cs}");
                }
                if (cmdVmstatPojo.getUs() != null){
                    VALUES("us","#{cmdVmstatPojo.us}");
                }
                if (cmdVmstatPojo.getSy() != null){
                    VALUES("sy","#{cmdVmstatPojo.sy}");
                }
                if (cmdVmstatPojo.getId() != null){
                    VALUES("id","#{cmdVmstatPojo.id}");
                }
                if (cmdVmstatPojo.getWa() != null){
                    VALUES("wa","#{cmdVmstatPojo.wa}");
                }
                if (cmdVmstatPojo.getSt() != null){
                    VALUES("st","#{cmdVmstatPojo.st}");
                }

            }

        }.toString();
    }

    public String selectvmstat(String vid){
        return new SQL() {
            {
                SELECT("*");
                FROM("vmstat");
                if (vid != null){
                    WHERE("vid=#{vid}");
                }
            }
        }.toString();
    }

    public String selectvm(String vid,int num){
        return new SQL() {
            {
                SELECT("*");
                FROM("vmstat");
                if (vid != null){
                    WHERE("vid=#{vid}");
                }else {
                    ORDER_BY("vid DESC LIMIT "+num+",20");
                }
            }
        }.toString();
    }

    public String file(FilePojo filePojo){
        return new SQL(){
            {
                INSERT_INTO("filedata");
                VALUES("newtime","#{newtime}");
                VALUES("name","#{name}");
                VALUES("serious","#{serious}");
                VALUES("priority","#{priority}");
                VALUES("state","#{state}");
                VALUES("title","#{title}");
                VALUES("iteration","#{iteration}");
                VALUES("platform","#{platform}");
                VALUES("solvetime","#{solvetime}");
                VALUES("design","#{design}");
            }
        }.toString();
    }
}
