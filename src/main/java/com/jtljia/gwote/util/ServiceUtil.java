package com.jtljia.gwote.util;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.basicservice.BasicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装日志
 */
public class ServiceUtil {

    private static Logger logger = LoggerFactory.getLogger(ServiceUtil.class);

    public static JSONObject execute(String methodName, String json, BasicService basicService){
        logger.info(methodName+">>>>>>>start:"+json);
        JSONObject resultJSON = new JSONObject();
        try {
            basicService.execute(resultJSON);
        }catch (Exception e){
            e.printStackTrace();
            logger.info(e.getMessage());
            resultJSON.put("status",1);
            resultJSON.put("msg",e.getMessage().split("\\r\\n")[1]);
        }
        logger.info(methodName+">>>>>>>end:"+resultJSON);
        return resultJSON;
    }

    public static Map<String,Object> getPageInfo(JSONObject parseObject){
        parseObject.put("start",Integer.valueOf(parseObject.getString("start")));
        parseObject.put("pageSize",Integer.valueOf(parseObject.getString("pageSize")));
        return parseObject;
    }
}
