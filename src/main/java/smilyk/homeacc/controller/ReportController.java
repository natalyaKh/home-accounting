package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.*;
import smilyk.homeacc.service.report.ReportService;

import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations reports to bills in HomeAccounting" )

@RequestMapping("v1/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @ApiOperation(value = "Search last operations by user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/last/{userUuid}")
    public List<LastOperationsDto> getUserByUserEmail(@RequestBody LastOperationsRequestDto dto,
                                                      @PathVariable String userUuid){
        return reportService.getAllLastOperations(dto, userUuid);
    }

}
