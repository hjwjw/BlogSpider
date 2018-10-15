package io.github.hjwjw.test;

import io.github.hjwjw.service.ILoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * The ControllerTest class.
 *
 * @author HJW
 * @date 2018/10/15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

    @Resource
    private ILoginService loginService;

    @Test
    public void testContoroller(){
        Map<String,Object> paramMap = new HashMap<>();
        Map<String, Object> map = loginService.getLoginParam("https://passport.csdn.net/account/login");
        if (null != map && map.size() > 0){
            paramMap.put("lt",map.get("lt"));
            paramMap.put("execution",map.get("execution"));
            paramMap.put("fkid",map.get("fkid"));
            paramMap.put("_eventId",map.get("_eventId"));
            paramMap.put("iframe",map.get("iframe"));
            paramMap.put("username","ftdd_hw");
            paramMap.put("password","hjwhjw+7856");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*if (loginService.LoginMyblog(map.get("action").toString(),paramMap)){
            System.out.println("登陆成功");
        }*/


    }
}
