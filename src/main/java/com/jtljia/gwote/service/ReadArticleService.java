package com.jtljia.gwote.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.util.ExecuterServiceUtil;
import com.jtljia.gwote.util.HttpUtil;
import com.jtljia.gwote.util.PropertiesUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class ReadArticleService {

    /**
     * 获取全部公告通知
     *
     * @return
     */
    public JSONArray getWaitedReadArcitle() {
        String getArticleUrl = PropertiesUtil.getProperty("get_article_url");
        String response = HttpUtil.doGet(getArticleUrl);
        if (StringUtils.isEmpty(response)) {
            return null;
        }
        JSONArray articles = JSONObject.parseArray(response);
        JSONArray waitedReadArticles = new JSONArray();
        articles.forEach((article) -> {
            JSONObject articleJSON = (JSONObject) article;
            String count = articleJSON.getString("Count");
            if (StringUtils.isEmpty(count)) {
                waitedReadArticles.add(article);
            }
        });
        return waitedReadArticles;
    }

    /**
     * 阅读未读公告通知
     *
     * @param waitedReadArticles 所有公告通知
     */
    public void readArticles(JSONArray waitedReadArticles) {
        ExecutorService executorService = ExecuterServiceUtil.getExecutorService();
        waitedReadArticles.forEach((article) -> {
            JSONObject articleJSON = (JSONObject) article;
            System.out.println("开始阅读：" + articleJSON.getString("Title"));
            Future<?> submit = executorService.submit(() -> {
                String notice_id = articleJSON.getString("Notice_Id");
                String url = PropertiesUtil.getProperty("read_article_url") + "&id=" + notice_id;
                return HttpUtil.doGet(url, notice_id);
            });
            try {
                String o = (String) submit.get();
                if (!StringUtils.isEmpty(o)) {
                    System.out.println("阅读成功！");
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 阅读企业发文
     *
     * @return
     */
    public JSONObject getWaitedReadEnterpriseArticles() {
        JSONObject resultJSON = new JSONObject();
        String url = PropertiesUtil.getProperty("get_enterprise_articles_url");
        String response = HttpUtil.doGet(url);
        System.out.println(response);
        try {
            if (!StringUtils.isEmpty(response)) {
                String replaceAll = response.replaceAll("\\\\", "");
                String substring = replaceAll.substring(1, replaceAll.length() - 1);
                JSONArray articles = JSONObject.parseArray(substring);
                articles.forEach((article) -> {
                    JSONObject articleJSON = (JSONObject) article;
                    String id = articleJSON.getString("id");
                    String readEnterpriseArticlesUrl = PropertiesUtil.getProperty("read_enterprise_articles_url");
                    System.out.println("开始阅读企业发文：" + articleJSON.getString("title"));
                    String articleInfo = HttpUtil.doGet(readEnterpriseArticlesUrl + id);
                    if (!StringUtils.isEmpty(articleInfo)) {
                        System.out.println("阅读成功！");
                    }
                });
                resultJSON.put("status", 1);
                resultJSON.put("msg", "阅读成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJSON;
    }
}
