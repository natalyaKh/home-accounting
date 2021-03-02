package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.inputCard.InputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@RequestMapping("v1/input")
public class InputCardController {

    @Autowired
    InputCardService inputCardService;

    @Autowired
    ValidatorService validatorService;

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

    @GetMapping("/{userUuid}")
    public List<InputCardDto> getAllInputCardsByUserUuid(@PathVariable String userUuid){
//        TODO test
        return this.inputCardService.getAllInputCardsByUserUuid(userUuid);
    }

    @DeleteMapping("/{inputCardUuid}")
    public InputCardDto deleteInputCard(@PathVariable String inputCardUuid){
        validatorService.checkInputForDeleted(inputCardUuid);
//        TODO test
        return this.inputCardService.deleteInputCard(inputCardUuid);
    }
}
