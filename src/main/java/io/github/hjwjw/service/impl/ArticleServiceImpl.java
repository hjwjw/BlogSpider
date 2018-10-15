package io.github.hjwjw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.hjwjw.entity.Article;
import io.github.hjwjw.service.IArticleService;
import io.github.hjwjw.utils.HttpClient4;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ArticleServiceImpl class.
 *
 * @author HJW
 * @date 2018/10/15
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Override
    public Map<String, String> getPostList(String url, String cookie) {
        Map<String, String> postMap = new HashMap<>();
        String page = HttpClient4.doGet(url,cookie);
        Document document = Jsoup.parse(page);
        Elements elements = document.body().select("tab-pane").select("article-list-item");
        if (elements.size() > 0){
            String articleId;
            String createDate;
            for (Element e : elements) {
                articleId = e.getElementsByClass("list-item-title").get(0)
                        .getElementsByTag("a").get(0).attr("href").split("/")[2];
                createDate = e.getElementsByClass("item-info-left").get(0)
                        .getElementsByTag("span").get(1).text();
                postMap.put(articleId,createDate);
            }
        }
        return postMap;
    }

    @Override
    public List<Article> toArticle(String url, String cookie, Map<String, String> postList) {
        List<Article> articleList = new ArrayList<>();
        String articleJson;
        for (Map.Entry<String, String> entry : postList.entrySet()){
            articleJson = HttpClient4.doGet(url+"?id="+entry.getKey(),cookie);
            JSONObject jsonObject = JSON.parseObject(articleJson);
            if ("true".equals(jsonObject.get("status"))){
                Article a = new Article();
                JSONObject jsonData = (JSONObject) jsonObject.get("data");
                if ("original".equals(jsonData.getString("type"))){
                    a.setTitle(jsonData.getString("title"));
                    a.setCategories(jsonData.getString("categories"));
                    a.setTags(jsonData.getString("tags"));
                    a.setPrivateStatus(jsonData.getString("private"));
                    a.setStatus(jsonData.getString("status"));
                    a.setType(jsonData.getString("type"));
                    a.setMarkdownContent(jsonData.getString("markdowncontent"));
                    a.setCreateDate(entry.getValue());
                }else{
                    continue;
                }
                articleList.add(a);
            }
        }
        return articleList;
    }
}
