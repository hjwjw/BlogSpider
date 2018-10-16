package io.github.hjwjw.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The LoginUtil class.
 *
 * @author HJW
 * @date 2018/10/16
 */
public class LoginUtil {

    public static Map<String,Object> getLoginParamMap(Map<String,Object> map){
        Map<String,Object> paramMap = new HashMap<>();
        if (null != map && map.size() > 0){
            paramMap.put("lt",map.get("lt"));
            paramMap.put("execution",map.get("execution"));
            paramMap.put("fkid",map.get("fkid"));
            paramMap.put("_eventId",map.get("_eventId"));
            paramMap.put("iframe",map.get("iframe"));
        }
        return paramMap;
    }

    public static String subCookie(String value){
        int end = value.indexOf(";");
        return value.substring(0,end+1);
    }
}
