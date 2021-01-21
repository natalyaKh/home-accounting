package smilyk.homeacc.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import smilyk.homeacc.utils.Utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class HealthControllerTest {
    @Autowired
    Utils utils;
    @Autowired
    HealthController healthController;

    @Test
    void getVersion() {
        String rez = healthController.getVersion();

        assertNotNull(rez);
        assertEquals("homeacc:0.0.1", rez);
    }
}