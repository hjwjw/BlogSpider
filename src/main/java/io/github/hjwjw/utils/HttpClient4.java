package io.github.hjwjw.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * The HttpClient4 class.
 *
 * @author HJW
 * @date 2018/10/14
 */
public class HttpClient4 {

    public static String responseCookie="";
    public static Header[] headers;

    public static String doGet(String url,String requestCookie) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            //通过默认配置创建一个httpclient
            httpClient = HttpClients.createDefault();
            //创建一个httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            //设置请求头，鉴权
//            httpGet.setHeader(HttpHeaders.HOST, "passport.csdn.net");
            httpGet.setHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
            httpGet.setHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            httpGet.setHeader(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            httpGet.setHeader(HttpHeaders.ACCEPT_ENCODING,"gzip, deflate, br");
            httpGet.setHeader(HttpHeaders.CONNECTION,"keep-alive");
            httpGet.setHeader("DNT","1");
            httpGet.setHeader("Upgrade-Insecure-Requests","1");
            httpGet.setHeader("cookie",requestCookie);
            //设置请求参数
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(35000)//连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)//请求超时时间
                    .setSocketTimeout(60000)//数据读取超时时间
                    .build();
            //为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            //执行get得到返回对象
            httpResponse = httpClient.execute(httpGet);
            headers = httpResponse.getAllHeaders();
            for (Header h : headers) {
                if ("Set-Cookie".equalsIgnoreCase(h.getName())){
                    responseCookie += subCookie(h.getValue());
                }
            }

            //通过返回对象获取返回数据
            HttpEntity entity = httpResponse.getEntity();
            //通过EntityUtil中的toString 方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String doPost(String url, Map<String, Object> paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:62.0) Gecko/20100101 Firefox/62.0");
        httpPost.setHeader(HttpHeaders.ACCEPT,"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpPost.setHeader(HttpHeaders.ACCEPT_LANGUAGE,"zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        httpPost.setHeader(HttpHeaders.ACCEPT_ENCODING,"gzip, deflate, br");
//        httpPost.setHeader("cookie",cookie);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)
                .setConnectionRequestTimeout(35000)
                .setSocketTimeout(60000)
                .build();
        httpPost.setConfig(requestConfig);
        //封装POST请求参数
        if (null != paramMap && paramMap.size() > 0) {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
            Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> mapEntry = iterator.next();
                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            httpResponse = httpClient.execute(httpPost);
            headers = httpResponse.getAllHeaders();
            for (Header h : headers) {
                if ("Set-Cookie".equalsIgnoreCase(h.getName())){
                    responseCookie += subCookie(h.getValue());
                }
            }
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String subCookie(String value){
        int end = value.indexOf(";");
        return value.substring(0,end+1);
    }
}
