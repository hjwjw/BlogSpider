package io.github.hjwjw.service;

import java.util.Map;

/**
 * The ILoginService class.
 *
 * @author HJW
 * @date 2018/10/14
 */
public interface ILoginService {

    /**
     * 获取首页登陆参数
     * @param url
     * @return
     */
    Map<String,Object> getLoginParam(String url);

    /**
     * 登陆博客
     * @param url   登陆链接
     * @param paramMap  参数
     * @return
     */
    String LoginMyblog(String url,Map<String,Object> paramMap);

}
