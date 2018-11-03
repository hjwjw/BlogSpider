package io.github.hjwjw.test;

import io.github.hjwjw.utils.ImageUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * The WeiboImgTest class.
 *
 * @author HJW
 * @date 2018/10/31
 */

public class WeiboImgTest{

    @Test
    public void imgTest(){
        StringBuffer sb = new StringBuffer();
        File file = new File("F:\\CODE\\Java\\myblogspider\\blog\\微服务\\微服务学习笔记--使用Feign实现声明式REST调用.md");
        try {
            FileReader fr = new FileReader(file);
            char[] buf = new char[(int) file.length()];
            while (fr.read(buf) > 0){
                sb.append(buf);
            }
            fr.close();
            ImageUtil.replaceImg(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
