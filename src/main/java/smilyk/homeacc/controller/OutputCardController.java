package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.dto.OutputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.outputCard.OutputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to output cards in HomeAccounting" )

@RequestMapping("v1/output")
public class OutputCardController {

    @Autowired
    OutputCardService outputCardService;
    @Autowired
    ValidatorService validatorService;

    /**
     * method create input card
     *
     * @param outputCardDto
     * @return inputCardDto
     */
    @ApiOperation(value = "Create output card by user", response = OutputCardDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OutputCardDto createOutputCard(@Validated @RequestBody OutputCardDto outputCardDto) {
        validatorService.checkBillByUser(outputCardDto.getBillName(), outputCardDto.getUserUuid());
        validatorService.checkBillByUserAndCurrency(
            outputCardDto.getBillName(), outputCardDto.getUserUuid(), outputCardDto.getCurrency());
        if (outputCardDto.getDiscount() == null) {
            outputCardDto.setDiscount(0.0);
        }
        if (outputCardDto.getNote() == null) {
            outputCardDto.setNote("");
        }
        if (outputCardDto.getUnit() == null) {
            outputCardDto.setUnit("");
        }
        Category category = validatorService.checkCategory(outputCardDto.getCategoryName(), outputCardDto.getUserUuid());
        Subcategory subcategory = validatorService
            .checkSubcategory(outputCardDto.getSubcategoryName(), outputCardDto.getUserUuid());
        outputCardDto.setCategoryUuid(category.getCategoryUuid());
        outputCardDto.setSubcategoryUuid(subcategory.getSubcategoryUuid());
        return this.outputCardService.createInputCard(
            outputCardDto);
    }

    @ApiOperation(value = "View a list of all output cards by user")
    @GetMapping("/{userUuid}")
    public List<OutputCardDto> getAllOutputCardsByUserUuid(@PathVariable String userUuid){
//        TODO test
        return this.outputCardService.getAllOutputCardsByUserUuid(userUuid);
    }

    @ApiOperation(value = "Search poutput card", response = OutputCardDto.class)
    @GetMapping("/{userUuid}/{outputCardUuid}")
    public OutputCardDto getOutputCardByUuid(@PathVariable String userUuid, @PathVariable String outputCardUuid){
//       TODO test
        return this.outputCardService.getOutputCardByUuid(userUuid, outputCardUuid);
    }

    @ApiOperation(value = "Delete output card", response = OutputCardDto.class)
    @DeleteMapping("/{outputCardUuid}")
    public OutputCardDto deleteOutputCard(@PathVariable String outputCardUuid){
        validatorService.checkOutputForDeleted(outputCardUuid);
//        TODO test
        return this.outputCardService.deleteOutputCard(outputCardUuid);
    }



}
