package com.jtljia.gwote.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.annotation.DataSource;
import com.jtljia.gwote.dao.local.LocalDao;
import com.jtljia.gwote.datasource.EnumDataSource;
import com.jtljia.gwote.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class LogService {
    private Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private LocalDao localDao;


    /**
     * 增加新流程
     * @param json
     * @return
     */
    @DataSource(dataSourceType = EnumDataSource.localDataSource)
    public JSONObject addWorkFlow(String json){
        return ServiceUtil.execute("addWorkFlow",json,resultJSON -> {
            JSONObject workflow = JSONObject.parseObject(json);
            localDao.insertSelctive(workflow);
            resultJSON.put("status",0);
            resultJSON.put("msg","添加成功！");
        });
    }

    /**
     * 删除新流程
     * @param json
     * @return
     */
    @DataSource(dataSourceType = EnumDataSource.localDataSource)
    public JSONObject deleteWorkFlow(String json){
        return ServiceUtil.execute("deleteWorkFlow",json,resultJSON -> {
            JSONObject workflow = JSONObject.parseObject(json);
            localDao.deleteById(workflow.getString("id"));
            resultJSON.put("status",0);
            resultJSON.put("msg","删除成功！");
        });
    }

    /**
     * 修改流程
     * @param json
     * @return
     */
    @DataSource(dataSourceType = EnumDataSource.localDataSource)
    public JSONObject updateWorkFlow(String json){
        return ServiceUtil.execute("updateWorkFlow",json,resultJSON -> {
            JSONObject workflow = JSONObject.parseObject(json);
            JSONArray prop = workflow.getJSONArray("prop");

            StringBuilder propString = new StringBuilder();
            String substring = "";
            if (!CollectionUtils.isEmpty(prop)){
                prop.forEach(item-> propString.append(item).append(","));
                substring = propString.toString().substring(0, propString.length() - 1);
            }

            String status = workflow.getString("status");
            if ("0".equals(status)){
                Date date = new Date(System.currentTimeMillis());
                workflow.put("onlineDate",date);
            }

            workflow.put("prop",substring);
            localDao.updateById(workflow);
            resultJSON.put("status",0);
            resultJSON.put("msg","修改成功！");
        });
    }

    /**
     * 查询流程
     * @param json
     * @return
     */
    @DataSource(dataSourceType = EnumDataSource.localDataSource)
    public JSONObject getWorkFlow(String json){
        return ServiceUtil.execute("getWorkFlow",json,resultJSON -> {
            JSONObject parseObject = JSONObject.parseObject(json);
            int workFlowCount = localDao.selectOnlineWorkFlowCount(parseObject.getInteger("status"));
            List<Map<String, Object>> workflows = localDao.selectSelective(parseObject);
            init(workflows);
            resultJSON.put("workflows",workflows);
            resultJSON.put("totalCount",workFlowCount);
            resultJSON.put("status",0);
            resultJSON.put("msg","查询成功！");
        });
    }

    /**
     * 格式化开始时间和上线时间
     * @param list
     */
    public void init(List<Map<String,Object>> list){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        list.forEach(item->{
            Timestamp startTime = (Timestamp) item.get("start_time");
            String format = dateFormat.format(startTime);
            item.put("start_time",format);

            Date onlineDate = (Date) item.get("online_time");
            if (!ObjectUtils.isEmpty(onlineDate)) {
                String formatDate = dateFormat.format(onlineDate);
                item.put("online_time",formatDate);
            }

            String prop = (String) item.get("prop");
            if(!StringUtils.isEmpty(prop)){
                String[] split = prop.split(",");
                item.put("prop",split);
            }else {
                item.put("prop",new String[0]);
            }

        });
    }

}
