package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to health of HomeAccounting" )

@RequestMapping("/ping")
public class HealthController {

    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

//checked
    @GetMapping()
    public String getVersion() {
        return applicationName + ":"  + buildVersion;
    }

}
