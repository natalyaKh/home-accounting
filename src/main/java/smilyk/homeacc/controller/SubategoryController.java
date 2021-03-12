package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.service.subcategory.SubcategoryService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to subcategory in HomeAccounting" )

@RequestMapping("v1/subcategory")
public class SubategoryController {
    @Autowired
    SubcategoryService subcategoryService;

    @Autowired
    ValidatorService validatorService;

    @ApiOperation(value = "Create subcategory", response = SubcategoryDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SubcategoryDto createSubcategory(@RequestBody SubcategoryDto subcategoryDto){
        //        TODO test
        validatorService.checkSubcategoryByName(subcategoryDto.getSubcategoryName(), subcategoryDto.getUserUuid());
        return subcategoryService.createSubcategory(subcategoryDto);
    }

    @ApiOperation(value = "View a list of all subcategories by user")
    @GetMapping("/user/{userUuid}")
    public List<SubcategoryDto> getAllSubcategoryByUser(@PathVariable String userUuid){
//        TODO test
        return subcategoryService.getAllSubcategoryByUserUuid(userUuid);
    }

    @ApiOperation(value = "Search a subcategory", response = SubcategoryDto.class)
    @GetMapping("/{subcategoryUuid}")
    public SubcategoryDto getSubcategoryBySubcategoryUuid(@PathVariable String subcategoryUuid){
        //        TODO test
        return subcategoryService.getSubcategoryBySubcategoryUuid(subcategoryUuid);
    }

    @DeleteMapping("/{subcategoryUuid}/{userUuid}")
    public SubcategoryDto deleteSubcategory(@PathVariable String subcategoryUuid, @PathVariable String userUuid){
        validatorService.checkSubcategoryByUuidForDeleted(subcategoryUuid, userUuid);
        //        TODO test
        return subcategoryService.deleteSubcategoryBySubcategoryUuid(subcategoryUuid);
    }
    @ApiOperation(value = "Update a subcategory", response = SubcategoryDto.class)
    @PutMapping()
    public SubcategoryDto updateSubcategory(@RequestBody SubcategoryDto subcategoryDto){
        validatorService.checkSubcategoryByUuidForDeleted(subcategoryDto.getSubcategoryUuid(), subcategoryDto.getUserUuid());
        //        TODO test
        return subcategoryService.updateSubcategory(subcategoryDto);
    }

    @ApiOperation(value = "Check subcategory for validation")
    @GetMapping("/valid/{subcategoryName}/{userUuid}")
    public Boolean getSubcategoryForValid(@PathVariable String subcategoryName, @PathVariable String userUuid){
        return subcategoryService.getSubcategoryForValidationUniqueName(userUuid, subcategoryName);
    }
}
