package com.jtljia.gwote.test;

import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.util.HttpUtil;
import org.junit.Test;

public class HttpTest {
    @Test
    public void testPost() {
        JSONObject param = new JSONObject();
        param.put("phoneNumber", "18235111011");
        param.put("password", "123456");
        System.out.println(param.toString());
        String result = HttpUtil.doPost("http://localhost:8888/psms/login", param.toString());
        System.out.println(result);
    }

    @Test
    public void ToUpperCase(){
        String str = "bzgssqsp";
        String upperCase = str.toUpperCase();
        System.out.println("DS-"+upperCase);
    }
}
