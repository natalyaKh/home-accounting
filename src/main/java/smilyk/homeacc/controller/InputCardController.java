package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.inputCard.InputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

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
//        if (outputCardDto.getNote() == null) {
//            outputCardDto.setNote("");
//        }
//        if (outputCardDto.getUnit() == null) {
//            outputCardDto.setUnit("");
//        }
        Category category = validatorService.checkCategory(inputCardDto.getCategoryName(), inputCardDto.getUserUuid());
        Subcategory subcategory = validatorService
            .checkSubcategory(inputCardDto.getSubCategoryName(), inputCardDto.getUserUuid());
        inputCardDto.setCategoryUuid(category.getCategoryUuid());
        inputCardDto.setSubCategoryUuid(subcategory.getSubcategoryUuid());
        return this.inputCardService.createInputCard(inputCardDto);

//        TODO test
    }

}
