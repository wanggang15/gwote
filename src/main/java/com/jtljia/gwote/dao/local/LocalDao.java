package com.jtljia.gwote.dao.local;

import java.util.List;
import java.util.Map;

public interface LocalDao {



     List<Map<String,Object>> selectAllDepartment();

     int insertSelctive(Map<String,Object> workflow);

     int deleteById(String id);

     int updateById(Map<String,Object> workflow) ;

     List<Map<String,Object>> selectSelective(Map<String ,Object> workflow);

     int selectOnlineWorkFlowCount(int status);
}
