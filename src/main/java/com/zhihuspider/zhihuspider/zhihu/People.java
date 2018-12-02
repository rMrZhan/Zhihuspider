package com.zhihuspider.zhihuspider.zhihu;

import com.zhihuspider.zhihuspider.util.UnicodeUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class People {
    CloseableHttpClient httpClient;
    public void init() {
        httpClient = HttpClients.createDefault();
    }
    public void getInfo(String url_token) throws IOException {
        this.init();
        HttpGet request = new HttpGet("https://www.zhihu.com/api/v4/members/"+url_token+"?include=locations,employments,gender,educations,business,voteup_count,thanked_Count,follower_count,following_count,cover_url,following_topic_count,following_question_count,following_favlists_count,following_columns_count,answer_count,articles_count,pins_count,question_count,commercial_question_count,favorite_count,favorited_count,marked_answers_count,marked_answers_text,message_thread_token,account_status,is_active,is_force_renamed,is_bind_sina,sina_weibo_url,sina_weibo_name,show_sina_weibo,is_blocking,is_blocked,is_following,is_followed,mutual_followees_count,vote_to_count,vote_from_count,thank_to_count,thank_from_count,thanked_count,description,hosted_live_count,participated_live_count,allow_message,industry_category,org_name,org_homepage,badge[?(type=best_answerer)].topics");
        request.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        String res = this.httpClient.execute(request, new BasicResponseHandler());
        res = UnicodeUtil.decodeUnicode(res);
        String regex = "(.*?),";
        Pattern p =Pattern.compile(regex);
        Matcher m = p.matcher(res);
        while (m.find()){
            System.out.println(m.group(1));
        }
    }
}
