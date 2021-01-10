package smilyk.homeacc.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import smilyk.homeacc.constants.SecurityConstants;
//import smilyk.homeacc.constants.SecurityConstants;

import java.util.Date;
import java.util.UUID;

@Configuration
public class Utils {

    public UUID generateUserUuid() {
        return UUID.randomUUID();
    }

    /**  Создание  emailVerificationToken **/
    public String generateEmailVerificationToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();
    }

}
