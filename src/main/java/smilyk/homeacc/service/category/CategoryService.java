package smilyk.homeacc.service.category;

import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.model.Category;


public interface CategoryService {
    Category save(CategoryDto categoryDto);
}
