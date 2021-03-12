package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.service.category.CategoryService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to categories in HomeAccounting" )

@RequestMapping("v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ValidatorService validatorService;

    @ApiOperation(value = "Create category", response = CategoryDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto){
        //        TODO test
        validatorService.checkCategoryByName(categoryDto.getCategoryName(), categoryDto.getUserUuid());
        return categoryService.createCategory(categoryDto);
    }

    @ApiOperation(value = "View a list of all categories by user")
    @GetMapping("/user/{userUuid}")
    public List<CategoryDto> getAllCategoryByUser(@PathVariable String userUuid){
//        TODO test
        return categoryService.getAllCategoryByUserUuid(userUuid);
    }
    @ApiOperation(value = "Search a category", response = CategoryDto.class)
    @GetMapping("/{categoryUuid}")
    public CategoryDto getCategoryByCategoryUuid(@PathVariable String categoryUuid){
        //        TODO test
        return categoryService.getCategoryByCategoryUuid(categoryUuid);
    }
    @ApiOperation(value = "Delete a category")
    @DeleteMapping("/{categoryUuid}/{userUuid}")
    public CategoryDto deleteCategory(@PathVariable String categoryUuid, @PathVariable String userUuid){
        validatorService.checkCategoryByNameForDeleted(categoryUuid, userUuid);
        //        TODO test
        return categoryService.deleteCategoryByCategoryUuid(categoryUuid);
    }

    @ApiOperation(value = "Update a category")
    @PutMapping()
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto){
        validatorService.checkCategoryByNameForDeleted(categoryDto.getCategoryUuid(), categoryDto.getUserUuid());
        //        TODO test
        return categoryService.updateCategory(categoryDto);
    }
    @ApiOperation(value = "Check category for validation")
    @GetMapping("/valid/{categoryUuid}/{userUuid}")
    public Boolean getCategoryForValid(@PathVariable String categoryUuid, @PathVariable String userUuid){
        return categoryService.getCategoryForValidationUniqueName(userUuid, categoryUuid);
    }
}
