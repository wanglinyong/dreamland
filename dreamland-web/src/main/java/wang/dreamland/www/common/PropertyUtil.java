package wang.dreamland.www.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 12903 on 2018/7/2.
 */
public class PropertyUtil {
    private static Properties props = new Properties();
    public static String getValue(String key) {
        return props.getProperty(key);
    }

    public static void updateProperties(String key, String value) {
        props.setProperty(key, value);
    }

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("qqconnectconfig.properties"));
        } catch (FileNotFoundException var1) {
            var1.printStackTrace();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public static void main(String[] args) {
       String providerId =  getValue("providerId");
       String appId =  getValue("app_ID");
       String appSecret =  PropertyUtil.getValue("app_KEY");
       System.out.println("providerId=========="+providerId+"=============appID=="+appId+"====appSecret==="+appSecret);

    }
}
