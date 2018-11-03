package io.github.hjwjw.utils;

import cn.echisan.wbp4j.Entity.ImageInfo;
import cn.echisan.wbp4j.WbpUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The ImageUtil class.
 *
 * @author HJW
 * @date 2018/11/01
 */
@Component
public class ImageUtil {

    /**
     * 匹配Markdown中的图片
     */
    private static Pattern p = Pattern.compile("!\\[(.*?)\\]\\((.*?)\\)");


    private static String imageUrl;

    private static String username;

    private static String password;

    private static String cookieSava;

    @Value("${imageUrl}")
    public  void setSaveUrl(String imageUrl) {
        ImageUtil.imageUrl = imageUrl;
    }
    @Value("${weibo.login.username}")
    public  void setUsername(String username) {
        ImageUtil.username = username;
    }
    @Value("${weibo.login.password}")
    public  void setPassword(String password) {
        ImageUtil.password = password;
    }
    @Value("${weibo.cookie.savaPath}")
    public  void setCookieSava(String cookieSava) {
        ImageUtil.cookieSava = cookieSava;
    }

    /**
     * 替换MarkDown文档中的图片
     *
     * @param content
     * @return
     */
    public static String replaceImg(String content) {
        Matcher matcher = p.matcher(content);
        List<String> imgList = new ArrayList<>();
        Map<String, String> imgInfoMap = new HashMap<>();
        while (matcher.find()) {
            System.out.println(matcher.group());
            imgList.add(matcher.group());
        }
        //取出图片src 与 title
        for (String s : imgList) {
            //使用src作为key,title有可能重复
            imgInfoMap.put(s.substring(s.indexOf("(") + 1, s.length() - 1), s.substring(s.indexOf("[") + 1, s.indexOf("]")));
        }
        //下载图片到本地存储,把原src地址与本地地址存放到Map
        Map<String, String> srcAndLocal = downloadImage(imgInfoMap, imageUrl);
        //将图片上传到微博图床，把原src地址与微博图床地址存放到Map
        Map<String, String> srcAndUrl = weiboUpload(srcAndLocal);
        //替换Markdown文档中的原src地址为微博图床地址
        for (Map.Entry<String, String> entry : srcAndUrl.entrySet()) {
            content = content.replace(entry.getKey(), "https:" + entry.getValue());
        }
        return content;
    }

    /**
     * 下载图片
     *
     * @param imgInfo
     * @param savePath
     * @return
     */
    public static Map<String, String> downloadImage(Map<String, String> imgInfo, String savePath) {
        File imageFile = new File(savePath);
        if (!imageFile.exists()){
            imageFile.mkdirs();
        }
        Map<String, String> srcAndlocal = new HashMap<>();
        for (Map.Entry<String, String> entry : imgInfo.entrySet()) {
            try {
                URL url = new URL(entry.getKey());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                DataInputStream in = new DataInputStream(connection.getInputStream());
                String time = String.valueOf(System.currentTimeMillis());
                File file = new File(savePath + "\\" + entry.getValue() + ".jpg");
                //使用title作文件名可能会覆盖重复的文件，如果有重复文件，则重复文件加上时间
                if (file.exists()) {
                    File newFile = new File(savePath + "\\" + entry.getValue() + time + ".jpg");
                    file = newFile;
                }
                DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[4096];
                int count = 0;
                while ((count = in.read(buffer)) > 0) {
                    out.write(buffer, 0, count);
                }
                srcAndlocal.put(entry.getKey(), file.getPath());
                out.close();
                in.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return srcAndlocal;
    }

    /**
     * 上传图片到微博图床
     *
     * @param srcAndLocal
     * @return
     */
    private static Map<String, String> weiboUpload(Map<String, String> srcAndLocal) {
        Map<String, String> srcAndUrl = new HashMap<>();
        try {
            for (Map.Entry<String, String> entry : srcAndLocal.entrySet()) {
                WbpUpload wbpUpload = null;
                ImageInfo upload = null;
                wbpUpload = WbpUpload.builder()
                        .setUsername(username)
                        .setPassword(password)
                        .setCookiePath(cookieSava)
                        .build();
                upload = wbpUpload.upload(entry.getValue());
                srcAndUrl.put(entry.getKey(), upload.getMiddle());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srcAndUrl;
    }

}
