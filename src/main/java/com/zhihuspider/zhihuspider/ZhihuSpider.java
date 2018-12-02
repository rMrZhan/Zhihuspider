package com.zhihuspider.zhihuspider;

import com.zhihuspider.zhihuspider.zhihu.Topic;



import java.io.IOException;
import java.net.URISyntaxException;


public class ZhihuSpider {
    final private static String url = "https://www.zhihu.com/api/v4/search_v3";    //请求路径
    final private static String limit = "100";    //话题个数
    public static void main(String[] args) {
        try {
            Topic.getTopic(url,limit);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
