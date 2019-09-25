package com.jtljia.gwote.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpUtil {

    public static String doGet(String url) {
        //1、创建一个httpclient
        HttpClient httpClient = new DefaultHttpClient();
        //2、获取请求路径
//        String relUrl = url+"?date="+date;
        //3、创建一个get请求
        HttpGet httpGet = new HttpGet(url);
        String authorization = PropertiesUtil.getProperty("Authorization");
        httpGet.setHeader("Authorization", authorization);
        httpGet.setHeader("Referer", PropertiesUtil.getProperty("Referer_prefix") + "" + PropertiesUtil.getProperty("Referer_suffix") + authorization.split(" ")[1]);
        //4、执行get请求
        HttpResponse httpResponse = null;
        String result = "";
        try {
            httpResponse = httpClient.execute(httpGet);
            //5、获取响应体
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doGet(String url, String id) {
        //1、创建一个httpclient
        HttpClient httpClient = new DefaultHttpClient();
        //2、获取请求路径
//        String relUrl = url+"?date="+date;
        //3、创建一个get请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Authorization", PropertiesUtil.getProperty("Authorization"));
        httpGet.setHeader("Referer", PropertiesUtil.getProperty("Referer_prefix") + id + PropertiesUtil.getProperty("Referer_suffix"));
        //4、执行get请求
        HttpResponse httpResponse = null;
        String result = "";
        try {
            httpResponse = httpClient.execute(httpGet);
            //5、获取响应体
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String doPost(String url, String content) {
        //1、创建一个httpclient
        CloseableHttpClient httpClient = HttpClients.custom().build();
        //2、创建一个httpPost
        HttpPost httpPost = new HttpPost(url);
        //3、设置请求体
        StringEntity stringEntity = new StringEntity(content, Charset.forName("utf-8"));
        stringEntity.setContentType(String.valueOf(ContentType.APPLICATION_JSON));
        httpPost.setEntity(stringEntity);
        //开始发送请求
        String responseString = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            responseString = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HttpClientUtils.closeQuietly(httpResponse);
        }
        return responseString;
    }

    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
