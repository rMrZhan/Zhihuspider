package com.zhihuspider.zhihuspider.zhihu;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Answer {
    CloseableHttpClient httpClient;
    public void init() {
        httpClient = HttpClients.createDefault();
    }
    public void getAnswer(String id) throws IOException {
        this.init();
        HttpGet request = new HttpGet("https://www.zhihu.com/api/v4/questions/"+id+"/answers?include=content,voteup_count&limit=268&offset=0&sort_by=default");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        String res = this.httpClient.execute(request, new BasicResponseHandler());
        String regex = "\"author\":(.*?)\"url_token\":\"(.*?)\",(.*?)name\":\"(.*?)\",(.*?)updated_time\":(.*?),(.*?)voteup_count\":(.*?),\"content\":(.*?)\",";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(res);
        while (m.find()) {
            System.out.println("url_token:" + m.group(2));
            System.out.println("名称:" + m.group(4));
            System.out.println("updated_time:" + m.group(6));
            System.out.println("voteup_count:" + m.group(8));
            String content = m.group(9).replaceAll("\\\\u([0-9a-f]{3,4})p?","");
            System.out.println("content:" + content);
            if (m.group(2).length() > 1) {
                new People().getInfo(m.group(2));
            }else {
                System.out.println("该用户为匿名用户");
            }
        }
    }
}
