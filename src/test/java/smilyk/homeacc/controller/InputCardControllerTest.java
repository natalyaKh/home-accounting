package smilyk.homeacc.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.enums.CategoryType;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.service.inputCard.InputCardService;
import smilyk.homeacc.service.validation.ValidatorService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class InputCardControllerTest {
    private static final String CATEGORY_NAME = "category";
    private static final String CATEGORY_UUID = "2222";
    private static final String USER_UUID = "4444";
    private static final String SUBCATEGORY_NAME = "subcategory";
    private static final String SUBCATEGORY_UUID = "3333";
    private InputCardDto inputCardDto;
    private InputCardDto inputCardDtoNullFields;
    private Category category;
    private Subcategory subcategory;
    @InjectMocks
    InputCardController inputCardController;

    @Mock
    InputCardService inputCardService;

    @Mock
    ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        inputCardDto = InputCardDto.builder()
            .billName("bill")
            .billUuid("1111")
            .currency(Currency.USA)
            .categoryName(CATEGORY_NAME)
            .categoryUuid(CATEGORY_UUID)
            .count(2.0)
            .discount(50.0)
            .subCategoryName(SUBCATEGORY_NAME)
            .subCategoryUuid(SUBCATEGORY_UUID)
            .sum(10.0)
            .note("this is note")
            .unit("p")
            .userUuid(USER_UUID)
            .build();
        inputCardDtoNullFields = InputCardDto.builder()
            .billName("bill")
            .billUuid("1111")
            .currency(Currency.USA)
            .categoryName(CATEGORY_NAME)
            .categoryUuid(CATEGORY_UUID)
            .count(2.0)
            .discount(null)
            .subCategoryName(SUBCATEGORY_NAME)
            .subCategoryUuid(SUBCATEGORY_UUID)
            .sum(10.0)
            .note(null)
            .unit(null)
            .userUuid(USER_UUID)
            .build();
        category = Category.builder()
            .categoryName("category")
            .deleted(false)
            .categoryUuid(CATEGORY_UUID)
            .description("this is category")
            .type(CategoryType.OUTPUT)
            .userUuid(USER_UUID)
            .build();
        subcategory = Subcategory.builder()
            .deleted(false)
            .description("this is subcategory")
            .subcategoryName(SUBCATEGORY_NAME)
            .subcategoryUuid(SUBCATEGORY_UUID)
            .build();
    }

    @Test
    void createInputCard() {
        when(inputCardService.createInputCard(any(InputCardDto.class))).thenReturn(inputCardDto);
        when(validatorService.checkCategory(anyString(), anyString())).thenReturn(category);
        when(validatorService.checkSubcategory(anyString(), anyString())).thenReturn(subcategory);
        InputCardDto restoredInputCardDto = inputCardController.createInputCard(inputCardDto);

        assertNotNull(restoredInputCardDto);
        assertEquals(inputCardDto, restoredInputCardDto);
        assertEquals(inputCardDto.getCategoryUuid(), restoredInputCardDto.getCategoryUuid());
    }
    @Test
    void nullFields(){
        when(inputCardService.createInputCard(any(InputCardDto.class))).thenReturn(inputCardDtoNullFields);
        when(validatorService.checkCategory(anyString(), anyString())).thenReturn(category);
        when(validatorService.checkSubcategory(anyString(), anyString())).thenReturn(subcategory);
        InputCardDto restoredInputCardDto = inputCardController.createInputCard(inputCardDtoNullFields);

        assertNotNull(restoredInputCardDto);
        assertEquals("", restoredInputCardDto.getUnit());
        assertEquals("", restoredInputCardDto.getNote());
        assertEquals(0.0, restoredInputCardDto.getDiscount());
    }

}
