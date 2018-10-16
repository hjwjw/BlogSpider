package io.github.hjwjw.service;

import io.github.hjwjw.entity.Article;

import java.util.List;

/**
 * The IMdService class.
 *
 * @author HJW
 * @date 2018/10/14
 */
public interface IMdService {

    /**
     * 将所有Aiticle写入MD文件
     * @param saveUrl 文件存放路径
     * @param articleList
     */
    Boolean writerMd(String saveUrl, List<Article> articleList);
}
