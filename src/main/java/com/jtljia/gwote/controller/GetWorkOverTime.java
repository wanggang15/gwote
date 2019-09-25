package com.jtljia.gwote.controller;

import com.jtljia.gwote.service.WorkOverTimeService;
import com.jtljia.gwote.util.HttpUtil;
import com.jtljia.gwote.util.PropertiesUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gwote")
public class GetWorkOverTime {

    @Autowired
    private WorkOverTimeService workOverTimeService;

    @GetMapping("/getOverWorkTime")
    public void getOverWorkTime(HttpServletResponse response) {
        try {
            //1、获取所有加班时间
            List<Map<String, Object>> overTimeList = workOverTimeService.getOverTimeList();
            //2、获取excel文件
            HSSFWorkbook excel = workOverTimeService.getExcel(overTimeList);
            //3、返回给客户端
            HttpUtil.setResponseHeader(response, PropertiesUtil.getProperty("fileName"));
            ServletOutputStream outputStream = response.getOutputStream();
            excel.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
