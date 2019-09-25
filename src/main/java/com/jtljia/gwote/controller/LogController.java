package com.jtljia.gwote.controller;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
