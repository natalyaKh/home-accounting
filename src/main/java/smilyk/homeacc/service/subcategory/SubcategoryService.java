package smilyk.homeacc.service.subcategory;

import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.model.Subcategory;

public interface SubcategoryService {
    Subcategory save(SubcategoryDto subcategoryDto);
}
