package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.OutputCardDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.outputCard.OutputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

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

    @GetMapping("/{userUuid}")
    public List<OutputCardDto> getAllOutputCardsByUserUuid(@PathVariable String userUuid){
//        TODO test
        return this.outputCardService.getAllOutputCardsByUserUuid(userUuid);
    }

    @GetMapping("/{userUuid}/{outputCardUuid}")
    public OutputCardDto getOutputCardByUuid(@PathVariable String userUuid, @PathVariable String outputCardUuid){
//       TODO test
        OutputCardDto x = this.outputCardService.getOutputCardByUuid(userUuid, outputCardUuid);
        return this.outputCardService.getOutputCardByUuid(userUuid, outputCardUuid);
    }

    @DeleteMapping("/{outputCardUuid}")
    public OutputCardDto deleteOutputCard(@PathVariable String outputCardUuid){
        validatorService.checkOutputForDeleted(outputCardUuid);
//        TODO test
        return this.outputCardService.deleteOutputCard(outputCardUuid);
    }



}
