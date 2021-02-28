package smilyk.homeacc.service.category;

import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.model.Category;

import java.util.List;


public interface CategoryService {
    Category save(CategoryDto categoryDto);

    List<CategoryDto> getAllCategoryByUserUuid(String userUuid);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto deleteCategoryByCategoryUuid(String categoryUuid);

    CategoryDto updateCategory(CategoryDto categoryDto);

    CategoryDto getCategoryByCategoryUuid(String categoryUuid);
}
