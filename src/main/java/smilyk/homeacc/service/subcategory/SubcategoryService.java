package smilyk.homeacc.service.subcategory;

import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    Subcategory save(SubcategoryDto subcategoryDto);

    List<SubcategoryDto> getAllSubcategoryByUserUuid(String userUuid);

    SubcategoryDto createSubcategory(SubcategoryDto subcategoryDto);

    SubcategoryDto getSubcategoryBySubcategoryUuid(String subcategoryUuid);

    SubcategoryDto deleteSubcategoryBySubcategoryUuid(String subcategoryUuid);

    SubcategoryDto updateSubcategory(SubcategoryDto subcategoryDto);

    Boolean getSubcategoryForValidationUniqueName(String userUuid, String subcategoryName);
}
