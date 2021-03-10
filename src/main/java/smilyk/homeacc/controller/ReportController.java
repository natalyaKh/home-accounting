package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.*;
import smilyk.homeacc.service.report.ReportService;

import java.util.List;

@RestController
@RequestMapping("v1/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @PostMapping("/last/{userUuid}")
    public List<LastOperationsDto> getUserByUserEmail(@RequestBody LastOperationsRequestDto dto,
                                                      @PathVariable String userUuid){
        return reportService.getAllLastOperations(dto, userUuid);
    }

}
