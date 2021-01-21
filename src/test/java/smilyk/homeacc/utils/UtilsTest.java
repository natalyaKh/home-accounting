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
        String token = Utils.generateEmailVerificationToken("1111");
        System.err.println(token);
        Boolean expiredToken = Utils.hasTokenExpired(token);

        assertNotNull(expiredToken);
        assertFalse(expiredToken);
    }

    @Test
    void hasTokenExpired() {
       String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMTExIiwiZXhwIjoxNjEyMDgwMjIyfQ.HuRyZ9ROE35P7xvEFIWmV9rVHhqvUt3_vNHQnS3pz7bX5LB4PJilESjH-uru41eYeKHVoZahH16P5ppSlYlydQ\n";
        Boolean expiredToken = Utils.hasTokenExpired(token);

        assertNotNull(expiredToken);
        assertFalse(expiredToken);
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
    }
}