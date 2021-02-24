package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.service.category.CategoryService;
import smilyk.homeacc.service.user.UserService;
import smilyk.homeacc.service.validation.ValidatorService;

import java.util.List;

@RestController
@RequestMapping("v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ValidatorService validatorService;

    @GetMapping("/{userUuid}")
    public List<CategoryDto> getAllCategoryByUser(@PathVariable String userUuid){
//        TODO test
        return categoryService.getAllCategoryByUserUuid(userUuid);
    }
}
