package com.jtljia.gwote.controller;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.service.LogService;
import com.jtljia.gwote.util.ExcelUtil;
import com.jtljia.gwote.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;

@RestController
@RequestMapping("/workflow")
@CrossOrigin
public class LogController {

    @Autowired
    private LogService logService;


    /**
     * 增加工作流
     * @param json
     * @return
     */
    @PostMapping("/addWorkFlow")
    public JSONObject addWorkFlow(@RequestBody String json){
        return logService.addWorkFlow(json);
    }

    /**
     * 删除工作流
     * @param json
     * @return
     */
    @PostMapping("/deleteWorkFlow")
    public JSONObject deleteWorkFlow(@RequestBody String json){
        return logService.deleteWorkFlow(json);
    }

    /**
     * 修改工作流
     * @param json
     * @return
     */
    @PostMapping("/updateWorkFlow")
    public JSONObject updateWorkFlow(@RequestBody String json){
        return logService.updateWorkFlow(json);
    }
    /**
     * 查询工作流
     * @param json
     * @return
     */
    @PostMapping("/getWorkFlow")
    public JSONObject getWorkFlow(@RequestBody String json){
        return logService.getWorkFlow(json);
    }

    /**
     * Excel方式导入
     * @param files
     * @return
     */
    @PostMapping("/importWorkFlow")
    public JSONObject importWorkFlow(@RequestParam("input-b1") MultipartFile[] files, HttpServletResponse response){
        return ServiceUtil.execute("importWorkFlowRequest","", resultJSON -> {
            ServletOutputStream outputStream = response.getOutputStream();
            System.out.println(files.length);
            for (MultipartFile file : files){
                ArrayList<ArrayList<String>> workflows = ExcelUtil.readExcel2(file.getInputStream(), 0, 1, 5);
                resultJSON.put("workflows",workflows);
            }
        });
    }
}
