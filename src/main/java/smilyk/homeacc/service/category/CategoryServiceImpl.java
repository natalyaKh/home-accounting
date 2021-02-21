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
}
