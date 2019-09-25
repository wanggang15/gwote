package com.jtljia.gwote.dao.work;

import java.util.List;
import java.util.Map;

public interface WorkDao {

    List<Map<String,Object>> selectAllEmployee(String id);

    List<Map<String,Object>> selectTopDepartments(String code);
}
