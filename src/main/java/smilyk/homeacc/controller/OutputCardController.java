package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.dto.OutputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.outputCard.OutputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

@RestController
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
    @PostMapping
    public OutputCardDto createInputCard(@Validated @RequestBody OutputCardDto outputCardDto) {
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
            .checkSubcategory(outputCardDto.getSubCategoryName(), outputCardDto.getUserUuid());
        outputCardDto.setCategoryUuid(category.getCategoryUuid());
        outputCardDto.setSubCategoryUuid(subcategory.getSubcategoryUuid());
        return this.outputCardService.createInputCard(
            outputCardDto);
    }

}
