package com.zhihuspider.zhihuspider;

import com.zhihuspider.zhihuspider.util.UnicodeUtil;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MyTest {
    @Test
    public void get() throws URISyntaxException, IOException {
        String url = "https://www.zhihu.com/api/v4/search_v3";    //请求路径
        String count = "https://www.zhihu.com/question/";
        HttpGet httpGet = new HttpGet();
        //构造路径参数
        List<NameValuePair> nameValuePairList = Lists.newArrayList();
        nameValuePairList.add(new BasicNameValuePair("t","general"));
        nameValuePairList.add(new BasicNameValuePair("q","四川大学"));
        nameValuePairList.add(new BasicNameValuePair("limit","15"));
        //构造请求路径，并添加参数
        URI uri = new URIBuilder(url).addParameters(nameValuePairList).build();
        //构造Headers
        List<Header> headerList = Lists.newArrayList();
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING,"gzip, deflate"));
        headerList.add(new BasicHeader(HttpHeaders.CONNECTION, "keep-alive"));
        //构造HttpClient
        HttpClient httpClient = HttpClients.custom().setDefaultHeaders(headerList).build();
        //构造HttpGet请求
        HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(uri).build();
        //获取结果
        HttpResponse httpResponse = httpClient.execute(httpUriRequest);
        //获取返回结果中的实体
        HttpEntity entity = httpResponse.getEntity();
        //查看页面内容结果
        String res = EntityUtils.toString(entity);
     //   rawHTMLContent = new String(rawHTMLContent .getBytes("UTF-8"),"unicode");
        //知乎字符转码
        res = UnicodeUtil.decodeUnicode(res);
        res = res.replaceAll("<em>|</em>","");
        String regex = "question\":(.*?)(\"id\":.*?)(.*?)\"name\":(.*?)},";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(res);
        while(m.find()){
            String id = m.group(3).replaceAll("\\s|\"","");
            System.out.println("id: "+id);
            System.out.println("标题："+m.group(4));
            return;
        }
        //关闭HttpEntity流
        EntityUtils.consume(entity);

    }
}
