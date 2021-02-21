package smilyk.homeacc.service.subcategory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.repo.SubcategoryRepository;
import smilyk.homeacc.utils.Utils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SubcategoryServiceImplTest {
    ModelMapper modelMapper = new ModelMapper();
    private Subcategory subcategory;
    @InjectMocks
    SubcategoryServiceImpl subcategoryService;
    @Mock
    SubcategoryRepository subcategoryRepository;
    @Mock
    Utils utils;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        subcategory = Subcategory.builder()
            .subcategoryUuid("1111")
            .subcategoryName("dress")
            .deleted(false)
            .userUuid("2222")
            .description("this is subcategory")
            .build();
    }

    @Test
    void save() {
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory);

        SubcategoryDto subcategoryDto = modelMapper.map(subcategory, SubcategoryDto.class);
        Subcategory storedSubcategory = subcategoryService.save(subcategoryDto);

        assertNotNull(storedSubcategory);
        assertEquals(subcategory.getSubcategoryName(), storedSubcategory.getSubcategoryName());
        assertNotNull(storedSubcategory.getSubcategoryUuid());
        assertNotNull(storedSubcategory.getUserUuid());

        verify(utils, times(1)).generateUserUuid();
        verify(subcategoryRepository, times(1)).save(any(Subcategory.class));

    }
}
