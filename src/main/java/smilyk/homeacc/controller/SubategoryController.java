package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.service.category.CategoryService;
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

    @GetMapping("/{userUuid}")
    public List<SubcategoryDto> getAllSubcategoryByUser(@PathVariable String userUuid){
//        TODO test
        return subcategoryService.getAllSubcategoryByUserUuid(userUuid);
    }
}
