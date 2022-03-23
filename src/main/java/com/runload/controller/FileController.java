package com.runload.controller;

import com.runload.service.WriteService;
import com.runload.unit.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@RestController
public class FileController {

    @Resource
    private WriteService writeService;

    @RequestMapping(value = "newfile")
    public Result file() throws IOException {
        writeService.file();
        Result result = new Result(200,"ok");
        return result;
    }

    @RequestMapping(value = "testdata")
    public Map selecttestdata(String iteration){
        Map testnamedata = writeService.testnamedata(iteration);

        return testnamedata;
    }
}
