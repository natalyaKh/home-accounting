package smilyk.homeacc.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void hasTokenNotExpired() {
        String token = utils.generateEmailVerificationToken("1111");
        System.err.println(token);
        Boolean expiredToken = utils.hasTokenExpired(token);

        assertNotNull(expiredToken);
        assertFalse(expiredToken);
    }

    @Test
//    TODO
    void hasTokenExpired() {
       String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzZTI2M2ZhZS0xYTZiLTQ0MzQtOGIzZC1iOWQ0Y2YzZjgxNjIiLCJleHAiOjE2MTEyMTgwOTd9.n1ooedMW-WRTvNTrBaPtj1BOyh0tHD7oi4iamwaqmGuCQa015nOQevcEAgBjWx9E2Dv0UZj4p-XzEwVCIiuE3A";
        Boolean expiredToken = utils.hasTokenExpired(token);

        assertNotNull(expiredToken);
        assertTrue(expiredToken);
    }

    @Test
    void generateUserUuid() {

        UUID userUuidFirst = utils.generateUserUuid();
        UUID userUuidSecond = utils.generateUserUuid();

        assertNotNull(userUuidFirst);
        assertNotNull(userUuidSecond);

    }

    @Test
    void generateEmailVerificationToken() {
        String token = utils.generateEmailVerificationToken("1111");
        assertNotNull(token);
    }
}