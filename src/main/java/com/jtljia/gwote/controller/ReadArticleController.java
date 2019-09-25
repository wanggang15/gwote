package com.jtljia.gwote.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtljia.gwote.service.ReadArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gwote")
public class ReadArticleController {

    @Autowired
    private ReadArticleService readArticleService;

    /**
     * 阅读公告通知
     *
     * @return
     */
    @GetMapping("/readArticles")
    public JSONObject readArticles() {
        JSONObject resultJSON = new JSONObject();
        JSONArray waitedReadArcitle = readArticleService.getWaitedReadArcitle();
        if (CollectionUtils.isEmpty(waitedReadArcitle)) {
            resultJSON.put("status", 0);
            resultJSON.put("msg", "未获取到待阅读文章");
        } else {
            readArticleService.readArticles(waitedReadArcitle);
            resultJSON.put("status", 1);
            resultJSON.put("msg", "已阅读");
        }
        return resultJSON;
    }

    /**
     * 阅读企业发文
     *
     * @return
     */
    @GetMapping("/readEnterpriseArticles")
    public JSONObject readEnterpriseArticles() {
        return readArticleService.getWaitedReadEnterpriseArticles();
    }

}
