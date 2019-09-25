package com.jtljia.gwote.service;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.annotation.DataSource;
import com.jtljia.gwote.dao.local.LocalDao;
import com.jtljia.gwote.dao.work.WorkDao;
import com.jtljia.gwote.datasource.EnumDataSource;
import com.jtljia.gwote.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HrService {

    @Autowired
    private WorkDao workDao;

    @Autowired
    private LocalDao localDao;


    @DataSource(dataSourceType = EnumDataSource.workDataSource)
    public List<Map<String, Object>> getPomsHr(String code){
          return workDao.selectTopDepartments(code);
    }

    @DataSource(dataSourceType = EnumDataSource.localDataSource)
    public List<Map<String, Object>> getLocalhr(){
        return localDao.selectAllDepartment();
    }

    /**
     * 测试数据源
     * @return
     */
    public JSONObject testDataSource(){
        return ServiceUtil.execute("testDataSource","",(resultJSON -> {
            List<Map<String, Object>> pomsHr = getPomsHr("503818");
            List<Map<String, Object>> localhr = getLocalhr();
            resultJSON.put("pomsDepartment:",pomsHr);
            resultJSON.put("localDepartment:",localhr);
            resultJSON.put("status",0);
            resultJSON.put("msg","查询成功");
        }));
    }
}
