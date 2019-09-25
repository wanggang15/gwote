package com.jtljia.gwote.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.util.HttpUtil;
import com.jtljia.gwote.util.PropertiesUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkOverTimeService {

    private void getOverTime(List<Map<String, Object>> overTimeList, String responseEnty) {
        String replaceAll = responseEnty.replaceAll("\\\\", "");
        JSONObject parseObject = JSONObject.parseObject(replaceAll.substring(1, replaceAll.length() - 1));
        JSONArray jsonArray = parseObject.getJSONArray("data");
        jsonArray.forEach((item) -> {
//            System.out.println(item);
            JSONObject jsonItem = (JSONObject) item;
            String offDuty = jsonItem.getString("OffDuty");
            if (!"".equals(offDuty) && offDuty != null) {
                int hours = 0;
                int minutes = 0;
                String[] split = offDuty.split(":");
                hours = Integer.parseInt(split[0]) - 20;
                minutes = Integer.parseInt(split[1]);
                if (hours >= 0) {
                    int overTime = hours * 60 + minutes;
                    int overHours = overTime % 30 > 20 ? overTime / 30 + 1 : overTime / 30;
                    if (overHours > 0) {
                        ((JSONObject) item).put("OverTime", overHours * 0.5);
                        overTimeList.add((Map<String, Object>) item);
                    }
                }
            }
        });
    }

    public List<Map<String, Object>> getOverTimeList() throws IOException {
        //用于存放加班时长
        List<Map<String, Object>> overTimeList = new ArrayList<>();
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int month = calendar.get(Calendar.MONTH);
        for (; month >= 0; month--) {
            calendar.set(Calendar.MONTH, month);
            String responseEnty = HttpUtil.doGet(PropertiesUtil.getProperty("work_over_time_url") + "?date=" + dateFormat.format(calendar.getTime()));
            getOverTime(overTimeList, responseEnty);
        }
        overTimeList.sort((a, b) -> {
            String a1 = (String) a.get("Date");
            String b1 = (String) b.get("Date");
            String[] split1 = a1.split("-");
            String[] split2 = b1.split("-");
            Integer montha = Integer.valueOf(split1[0]);
            Integer monthb = Integer.valueOf(split2[0]);
            Integer daya = Integer.valueOf(split1[1]);
            Integer dayb = Integer.valueOf(split2[1]);
            if (montha - monthb > 0) {
                return 1;
            } else if (montha - monthb == 0) {
                return daya - dayb > 0 ? 1 : -1;
            } else if (montha - monthb < 0) {
                return -1;
            } else {
                return 0;
            }
        });
        overTimeList.add(getTotalHours(overTimeList));
        return overTimeList;
    }

    private Map<String, Object> getTotalHours(List<Map<String, Object>> overTimeList) {
        Double totalHours = 0.0;
        int size = overTimeList.size();
        for (Map<String, Object> stringObjectMap : overTimeList) {
            Double overTime = (Double) stringObjectMap.get("OverTime");
            totalHours += overTime;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("totalHours", totalHours);
        return map;
    }

    public HSSFWorkbook getExcel(List<Map<String, Object>> list) {
        //1、创建一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2、创建一个sheet
        HSSFSheet sheet = workbook.createSheet();
        //3、添加表头
        HSSFRow title = sheet.createRow(0);
        title.setHeightInPoints(30);
        //4、创建列对象
        HSSFCell titleCell1 = title.createCell(0);
        titleCell1.setCellValue("日期");
        title.createCell(1).setCellValue("上班时间");
        title.createCell(2).setCellValue("下班时间");
        title.createCell(3).setCellValue("加班时长");
        //5、添加行
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            Map<String, Object> map = list.get(i);
            row.createCell(0).setCellValue(String.valueOf(map.get("Date")));
            row.createCell(1).setCellValue(String.valueOf(map.get("OnDuty")));
            row.createCell(2).setCellValue(String.valueOf(map.get("OffDuty")));
            row.createCell(3).setCellValue(String.valueOf(map.get("OverTime")));
        }
        HSSFRow row = sheet.createRow(size);
        row.createCell(0).setCellValue("加班总时长");
        row.createCell(1).setCellValue(String.valueOf(list.get(size - 1).get("totalHours")));
        return workbook;
    }
}
