package smilyk.homeacc.service.category;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.constants.CategorySubcategoryConstant;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.repo.CategoryRepository;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    Utils utils;

    @Override
    public Category save(CategoryDto categoryDto) {
        return createCategoryForSaving(categoryDto);
    }

    @Override
    public List<CategoryDto> getAllCategoryByUserUuid(String userUuid) {
//        TODO test
        Optional<List<Category>> categoryOptional = categoryRepository.findByUserUuid(userUuid);
        return categoryOptional.map(categories -> categories.stream().map(this::categoryToCategoryDto)
            .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = createCategoryForSaving(categoryDto);
//        TODO test
        return modelMapper.map(category, CategoryDto.class);
    }

    private Category createCategoryForSaving(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryUuid(utils.generateUserUuid().toString());
        category.setDeleted(false);
        Category savedCategory = categoryRepository.save(category);
        LOGGER.info(CategorySubcategoryConstant.CATEGORY_WITH_NAME + category.getCategoryName() +
            CategorySubcategoryConstant.CREATED);
        return category;
    }

    @Override
    public CategoryDto deleteCategoryByCategoryUuid(String categoryUuid) {
        //        TODO test
        Optional<Category> category = categoryRepository.findByCategoryUuid(categoryUuid);
        categoryRepository.delete(category.get());
        LOGGER.info(CategorySubcategoryConstant.CATEGORY_WITH_UUID + categoryUuid + CategorySubcategoryConstant.DELETED);
        return modelMapper.map(category.get(), CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        //        TODO test
        Optional<Category> optionalCategory = categoryRepository.findByCategoryUuidAndUserUuid(categoryDto.getCategoryUuid(),
            categoryDto.getUserUuid());
        Category category = optionalCategory.get();
        category.setDescription(categoryDto.getDescription());
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);
        LOGGER.info(CategorySubcategoryConstant.CATEGORY_WITH_UUID + categoryDto.getCategoryUuid()
            + CategorySubcategoryConstant.UPDATED);
        return categoryDto;
    }

    @Override
    public CategoryDto getCategoryByCategoryUuid(String categoryUuid) {
        //        TODO test
     Optional<Category> optionalCategory = categoryRepository.findByCategoryUuid(categoryUuid);
     if (!optionalCategory.isPresent()){
         return CategoryDto.builder().build();
     }
        return  modelMapper.map(optionalCategory.get(), CategoryDto.class);
    }

    @Override
    public Boolean getCategoryForValidationUniqueName(String userUuid, String categoryName) {
        Optional<Category> optionalCategory = categoryRepository.findByCategoryNameAndUserUuid(categoryName, userUuid);
        if(optionalCategory.isPresent()){
            LOGGER.info(CategorySubcategoryConstant.CATEGORY_WITH_NAME + categoryName
                + CategorySubcategoryConstant.FOR_USER + userUuid + CategorySubcategoryConstant.EXISTS);
            return true;
        }else{
            return false;
        }
    }

    private CategoryDto categoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

}
