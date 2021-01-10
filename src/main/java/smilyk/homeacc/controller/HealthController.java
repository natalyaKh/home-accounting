package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class HealthController {

    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;


    @GetMapping()
    public String getVersion() {
        return applicationName + ":"  + buildVersion;
    }

}
