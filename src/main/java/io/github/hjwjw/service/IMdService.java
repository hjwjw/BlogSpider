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
     * @param articleList
     */
    Boolean writerMd(List<Article> articleList);
}
