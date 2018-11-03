package io.github.hjwjw.test;

import io.github.hjwjw.MyblogspiderApplication;
import io.github.hjwjw.utils.ImageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyblogspiderApplication.class)
@WebAppConfiguration
public class WeiboImgTest{

    @Test
    public void imgTest(){
        StringBuffer sb = new StringBuffer();
        File file = new File("F:\\CODE\\Java\\myblogspider\\blog\\Activiti5\\六Activiti5数据库表简要分析.md");
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
