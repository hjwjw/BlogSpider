package io.github.hjwjw.service;

import io.github.hjwjw.entity.Article;

import java.util.List;
import java.util.Map;

/**
 * The IArticleService class.
 *
 * @author HJW
 * @date 2018/10/14
 */
public interface IArticleService {

    /**
     * 文章列表链接-发表时间
     * @return
     */
    Map<String,String> getPostList(String url, String cookie);

    /**
     * 获取文章MD内容存入Article
     * @param postMap
     * @return
     */
    List<Article> toArticle(String url, String cookie, Map<String,String> postMap);

}
