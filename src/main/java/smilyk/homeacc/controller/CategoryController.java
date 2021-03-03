package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.service.category.CategoryService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@RequestMapping("v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ValidatorService validatorService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto){
        //        TODO test
        validatorService.checkCategoryByName(categoryDto.getCategoryName(), categoryDto.getUserUuid());
        return categoryService.createCategory(categoryDto);
    }

    @GetMapping("/user/{userUuid}")
    public List<CategoryDto> getAllCategoryByUser(@PathVariable String userUuid){
//        TODO test
        return categoryService.getAllCategoryByUserUuid(userUuid);
    }

    @GetMapping("/{categoryUuid}")
    public CategoryDto getCategoryByCategoryUuid(@PathVariable String categoryUuid){
        //        TODO test
        return categoryService.getCategoryByCategoryUuid(categoryUuid);
    }

    @DeleteMapping("/{categoryUuid}/{userUuid}")
    public CategoryDto deleteCategory(@PathVariable String categoryUuid, @PathVariable String userUuid){
        validatorService.checkCategoryByNameForDeleted(categoryUuid, userUuid);
        //        TODO test
        return categoryService.deleteCategoryByCategoryUuid(categoryUuid);
    }

    @PutMapping()
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto){
        validatorService.checkCategoryByNameForDeleted(categoryDto.getCategoryUuid(), categoryDto.getUserUuid());
        //        TODO test
        return categoryService.updateCategory(categoryDto);
    }
    @GetMapping("/valid/{categoryUuid}/{userUuid}")
    public Boolean getUserByUserEmail(@PathVariable String categoryUuid, @PathVariable String userUuid){
        return categoryService.getCategoryForValidationUniqueName(userUuid, categoryUuid);
    }
}
