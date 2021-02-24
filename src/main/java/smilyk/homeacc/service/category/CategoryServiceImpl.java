package smilyk.homeacc.service.category;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import java.util.stream.DoubleStream;

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
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCategoryUuid(utils.generateUserUuid().toString());
        category.setDeleted(false);
        Category savedCategory = categoryRepository.save(category);
        LOGGER.info(CategorySubcategoryConstant.CATEGORY_WITH_NAME + category.getCategoryName() +
            CategorySubcategoryConstant.CREATED);
        return savedCategory;
    }

    @Override
    public List<CategoryDto> getAllCategoryByUserUuid(String userUuid) {
//        TODO test
        Optional<Category> categoryOptional = categoryRepository.findByUserUuid(userUuid);
        if(!categoryOptional.isPresent()){
            return new ArrayList<>();
        }
        List<CategoryDto> categoryDtoList = categoryOptional.stream().map(this::categoryToCategoryDto)
            .collect(Collectors.toList());
        return categoryDtoList;
    }

    private  CategoryDto categoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }


}
