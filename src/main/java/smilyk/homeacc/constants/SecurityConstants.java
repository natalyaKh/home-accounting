package smilyk.homeacc.constants;

import smilyk.homeacc.security.AppProperties;
import smilyk.homeacc.SpringApplicationContext;


public class SecurityConstants {
//    TODO return time expiration 10 days
//    public static final long EXPIRATION_TIME =600000; //tmp for test
    public static final long EXPIRATION_TIME = 864000000; // 10 days;
    public static final String SIGN_UP_URL = "/v1/user/";
    public static final String CHECK_HEALTH ="/ping" ;
    public static final String VERIFICATION_EMAIL = "/v1/user/email-verification";
    public static final String CHECK_USER_FOR_VALIDATION = "/v1/user/valid/**";

    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";



    public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties)
                SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
