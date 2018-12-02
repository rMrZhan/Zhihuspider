package com.zhihuspider.zhihuspider.zhihu;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopicCount {
    CloseableHttpClient httpClient;
    public void init() {
        httpClient = HttpClients.createDefault();
    }
    public void getCount(String id) throws IOException {
        this.init();
        HttpGet request = new HttpGet("https://www.zhihu.com/question/"+id);
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        String res = this.httpClient.execute(request, new BasicResponseHandler());
        String regex = "NumberBoard-itemValue\" title=\"(.*?)\"";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(res);
        m.find();
        System.out.println("关注者:"+m.group(1));
        m.find();
        System.out.println("被浏览:"+m.group(1));

        String regex2 = "\"answerCount\":(\\d+),";
        Pattern p2 = Pattern.compile(regex2);
        Matcher m2 = p2.matcher(res);
        m2.find();
        System.out.println("回答数："+m2.group(1));


    }
}
