package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.service.subcategory.SubcategoryService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@RequestMapping("v1/subcategory")
public class SubategoryController {
    @Autowired
    SubcategoryService subcategoryService;

    @Autowired
    ValidatorService validatorService;

    @PostMapping
    public SubcategoryDto createSubcategory(@RequestBody SubcategoryDto subcategoryDto){
        //        TODO test
        validatorService.checkSubcategoryByName(subcategoryDto.getSubcategoryName(), subcategoryDto.getUserUuid());
        return subcategoryService.createSubcategory(subcategoryDto);
    }

    @GetMapping("/user/{userUuid}")
    public List<SubcategoryDto> getAllSubcategoryByUser(@PathVariable String userUuid){
//        TODO test
        return subcategoryService.getAllSubcategoryByUserUuid(userUuid);
    }

    @GetMapping("/{subcategoryUuid}")
    public SubcategoryDto getSubcategoryBySubcategoryUuid(@PathVariable String subcategoryUuid){
        //        TODO test
        return subcategoryService.getSubcategoryBySubcategoryUuid(subcategoryUuid);
    }

    @DeleteMapping("/{subcategoryUuid}/{userUuid}")
    public SubcategoryDto deleteSubcategory(@PathVariable String subcategoryUuid, @PathVariable String userUuid){
        validatorService.checkSubcategoryByNameForDeleted(subcategoryUuid, userUuid);
        //        TODO test
        return subcategoryService.deleteSubcategoryBySubcategoryUuid(subcategoryUuid);
    }

    @PutMapping()
    public SubcategoryDto updateSubcategory(@RequestBody SubcategoryDto subcategoryDto){
        validatorService.checkCategoryByNameForDeleted(subcategoryDto.getSubcategoryName(), subcategoryDto.getUserUuid());
        //        TODO test
        return subcategoryService.updateSubcategory(subcategoryDto);
    }
}
