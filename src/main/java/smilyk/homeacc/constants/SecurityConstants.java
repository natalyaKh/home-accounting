package smilyk.homeacc.constants;

import smilyk.homeacc.security.AppProperties;
import smilyk.homeacc.SpringApplicationContext;


public class SecurityConstants {
//    TODO return time expiration 10 days
//    public static final long EXPIRATION_TIME =600000; //tmp for test
    public static final long EXPIRATION_TIME = 864000000; // 10 days;

    public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties)
                SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
