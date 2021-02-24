package smilyk.homeacc.service.subcategory;

import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.model.Subcategory;

import java.util.List;

public interface SubcategoryService {
    Subcategory save(SubcategoryDto subcategoryDto);

    List<SubcategoryDto> getAllSubcategoryByUserUuid(String userUuid);
}
