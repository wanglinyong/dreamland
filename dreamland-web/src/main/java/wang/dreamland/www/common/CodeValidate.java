package wang.dreamland.www.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 12903 on 2018/6/30.
 */
public class CodeValidate {
    public static boolean validateCode(String code,String cacheCode){
        if(StringUtils.isNotBlank(code) && code.equals(cacheCode)){
            return true;
        }
        return false;
    }
}
