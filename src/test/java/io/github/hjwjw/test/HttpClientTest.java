package io.github.hjwjw.test;

import io.github.hjwjw.utils.HttpClient4;
import org.apache.http.Header;
import org.junit.Test;

/**
 * The HttpClientTest class.
 *
 * @author HJW
 * @date 2018/10/14
 */
public class HttpClientTest {

    @Test
    public void testHttpClient(){
        String res = HttpClient4.doGet("https://passport.csdn.net/account/login","");
        System.out.println("cookie:" + HttpClient4.responseCookie);
        for (Header h: HttpClient4.headers
             ) {
            System.out.println(h.getName() + ": " + h.getValue());
        }
        System.out.println(res);
    }
}
