package com.jtljia.gwote.controller;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.service.HrService;
import com.jtljia.gwote.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/hr")
public class HrController {

    @Autowired
    private HrService hrService;

    @GetMapping("/testDataSource")
    public JSONObject testDataSource(){
         return ServiceUtil.execute("testDataSource","",(resultJSON -> {
            List<Map<String, Object>> pomsHr = hrService.getPomsHr("");
            List<Map<String, Object>> localhr = hrService.getLocalhr();
            resultJSON.put("pomsDepartment:",pomsHr);
            resultJSON.put("localDepartment:",localhr);
            resultJSON.put("status",0);
            resultJSON.put("msg","查询成功");
        }));
    }

    @GetMapping("/updataDepartment")
    public JSONObject updataDepartment(){
        return ServiceUtil.execute("updataDepartment","",resultJSON -> {
            List<Map<String, Object>> pomsHr = hrService.getPomsHr("");
        });
    }
}
