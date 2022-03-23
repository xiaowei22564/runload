package com.runload.mapper;

import com.alibaba.fastjson.JSONObject;
import com.runload.pojo.FilePojo;
import com.runload.pojo.VmstatData;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface CmdVmstatMapper {

    @InsertProvider(type = CmdVmstatMapperlmpl.class,method = "vmstat")
    public boolean vmstat(VmstatData cmdVmstatPojo, String dateName);

    @SelectProvider(type = CmdVmstatMapperlmpl.class,method = "selectvmstat")
    public List<Map<String,String>> selectvmstat(String vid);

    @SelectProvider(type = CmdVmstatMapperlmpl.class,method = "selectvm")
    public List<JSONObject> selectvm(String vid, int num);

    @Select("SELECT COUNT(*) FROM `vmstat`")
    public int vmcount();

    @InsertProvider(type = CmdVmstatMapperlmpl.class,method = "file")
    public boolean file(FilePojo filePojo);

    @Select("SELECT name,COUNT(`name`) as 'data' FROM `filedata` WHERE iteration=#{iteration} GROUP BY name")
    public List<JSONObject> selectfiledata(String iteration);
}
