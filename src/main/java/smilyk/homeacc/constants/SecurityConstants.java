package smilyk.homeacc.constants;

import smilyk.homeacc.security.AppProperties;
import smilyk.homeacc.SpringApplicationContext;


public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000; // 10 days;

    public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties)
                SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
