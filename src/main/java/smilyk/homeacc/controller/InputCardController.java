package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.inputCard.InputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to income cards in HomeAccounting" )

@RequestMapping("v1/input")
public class InputCardController {

    @Autowired
    InputCardService inputCardService;

    @Autowired
    ValidatorService validatorService;

    @ApiOperation(value = "Create income card by user", response = InputCardDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public InputCardDto createInputCard(@Validated @RequestBody InputCardDto inputCardDto) {
        validatorService.checkBillByUser(inputCardDto.getBillName(), inputCardDto.getUserUuid());
        validatorService.checkBillByUserAndCurrency(
            inputCardDto.getBillName(), inputCardDto.getUserUuid(), inputCardDto.getCurrency());
//        if (outputCardDto.getDiscount() == null) {
//            outputCardDto.setDiscount(0.0);
//        }
        if (inputCardDto.getNote() == null) {
            inputCardDto.setNote("");
        }
        if (inputCardDto.getUnit() == null) {
            inputCardDto.setUnit("");
        }
        Category category = validatorService.checkCategory(inputCardDto.getCategoryName(), inputCardDto.getUserUuid());
        Subcategory subcategory = validatorService
            .checkSubcategory(inputCardDto.getSubcategoryName(), inputCardDto.getUserUuid());
        inputCardDto.setCategoryUuid(category.getCategoryUuid());
        inputCardDto.setSubcategoryUuid(subcategory.getSubcategoryUuid());
        return this.inputCardService.createInputCard(inputCardDto);

//        TODO test
    }

    @ApiOperation(value = "View a list of all input cards by user")
    @GetMapping("/{userUuid}")
    public List<InputCardDto> getAllInputCardsByUserUuid(@PathVariable String userUuid){
//        TODO test
        return this.inputCardService.getAllInputCardsByUserUuid(userUuid);
    }

    @ApiOperation(value = "Delete input card", response = InputCardDto.class)
    @DeleteMapping("/{inputCardUuid}")
    public InputCardDto deleteInputCard(@PathVariable String inputCardUuid){
        validatorService.checkInputForDeleted(inputCardUuid);
//        TODO test
        return this.inputCardService.deleteInputCard(inputCardUuid);
    }
    @ApiOperation(value = "Search input card", response = InputCardDto.class)
    @GetMapping("/{userUuid}/{inputCardUuid}")
    public InputCardDto getInputCardByUuid(@PathVariable String userUuid, @PathVariable String inputCardUuid){
//       TODO test
        return this.inputCardService.getInputCardByUuid(userUuid, inputCardUuid);
    }
}
