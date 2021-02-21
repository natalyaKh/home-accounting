package smilyk.homeacc.service.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.enums.CategoryType;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.repo.CategoryRepository;
import smilyk.homeacc.utils.Utils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {
    ModelMapper modelMapper = new ModelMapper();
    private Category category;
    @InjectMocks
    CategoryServiceImpl categoryService;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    Utils utils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        category = Category.builder()
            .categoryName("Products")
            .categoryUuid("1111")
            .userUuid("2222")
            .deleted(false)
            .description("")
            .type(CategoryType.OUTPUT)
            .build();
    }

    @Test
    void testCreateCategory() {
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        Category storedCategory = categoryService.save(categoryDto);

        assertNotNull(storedCategory);
        assertEquals(category.getCategoryName(), storedCategory.getCategoryName());
        assertNotNull(storedCategory.getCategoryUuid());
        assertNotNull(storedCategory.getUserUuid());

        verify(utils, times(1)).generateUserUuid();
        verify(categoryRepository, times(1)).save(any(Category.class));

    }
}
