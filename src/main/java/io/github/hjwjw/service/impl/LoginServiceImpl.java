package io.github.hjwjw.service.impl;

import io.github.hjwjw.service.ILoginService;
import io.github.hjwjw.utils.HttpClient4;
import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The LoginServiceImpl class.
 *
 * @author HJW
 * @date 2018/10/14
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Override
    public Map<String, Object> getLoginParam(String url) {
        Map<String,Object> paramMap = new HashMap<>();
        String loginPage = HttpClient4.doGet(url,"");
        Document document = Jsoup.parse(loginPage);
        if (ObjectUtils.isEmpty(document)){
            return paramMap;
        }else {
            paramMap.put("cookie",HttpClient4.responseCookie);
            paramMap.put("headers",HttpClient4.headers);
            paramMap.put("action",document.body().getElementsByTag("form").get(0).attr("action"));
            document.body().getElementsByTag("form").get(0).select("input").forEach(ele -> {
                if ("gps".equals(ele.attr("name"))){
                    paramMap.put("gps",ele.attr("value"));
                }else if ("lt".equals(ele.attr("name"))){
                    paramMap.put("lt",ele.attr("value"));
                }else if ("execution".equals(ele.attr("name"))){
                    paramMap.put("execution",ele.attr("value"));
                }else if ("fkid".equals(ele.attr("name"))){
                    paramMap.put("fkid",ele.attr("value"));
                }else if ("_eventId".equals(ele.attr("name"))){
                    paramMap.put("_eventId",ele.attr("value"));
                }else if ("iframe".equals(ele.attr("name"))){
                    paramMap.put("iframe",ele.attr("value"));
                }
            });
        }
        return paramMap;
    }

    @Override
    public String LoginMyblog(String url, Map<String, Object> paramMap) {
        String postCookie="";
        String successPage = HttpClient4.doPost(url,paramMap);
        postCookie = HttpClient4.responseCookie;
        System.out.println("cookie:" + HttpClient4.responseCookie);
        System.out.println("---------------------------");
        for (Header h: HttpClient4.headers
                ) {
            System.out.println(h.getName() + ": " + h.getValue());
        }
        System.out.println(successPage);
        return postCookie;
    }


}
