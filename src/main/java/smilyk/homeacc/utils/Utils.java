package smilyk.homeacc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.SecurityConstants;

import java.util.Date;
import java.util.UUID;

@Service
public class Utils {

    /**    проверка  срока действительности токена  **/
    public static boolean hasTokenExpired(String token) {
        boolean returnValue = false;
        try {
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token)
                    .getBody();
            Date tokenExpirationDate = claims.getExpiration();
            Date todayDate = new Date();
            returnValue = tokenExpirationDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            returnValue = true;
        }
        return returnValue;
    }

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
