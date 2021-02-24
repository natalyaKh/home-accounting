package smilyk.homeacc.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smilyk.homeacc.dto.OutputCardDto;
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

class OutputCardControllerTest {
    private static final String CATEGORY_NAME = "category";
    private static final String CATEGORY_UUID = "2222";
    private static final String USER_UUID = "4444";
    private static final String SUBCATEGORY_NAME = "subcategory";
    private static final String SUBCATEGORY_UUID = "3333";
    private OutputCardDto outputCardDto;
    private OutputCardDto outputCardDtoNullFields;
    private Category category;
    private Subcategory subcategory;
    @InjectMocks
    OutputCardController outputCardController;

    @Mock
    InputCardService inputCardService;

    @Mock
    ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        outputCardDto = OutputCardDto.builder()
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
        outputCardDtoNullFields = OutputCardDto.builder()
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
        when(inputCardService.createInputCard(any(OutputCardDto.class))).thenReturn(outputCardDto);
        when(validatorService.checkCategory(anyString(), anyString())).thenReturn(category);
        when(validatorService.checkSubcategory(anyString(), anyString())).thenReturn(subcategory);
        OutputCardDto restoredOutputCardDto = outputCardController.createInputCard(outputCardDto);

        assertNotNull(restoredOutputCardDto);
        assertEquals(outputCardDto, restoredOutputCardDto);
        assertEquals(outputCardDto.getCategoryUuid(), restoredOutputCardDto.getCategoryUuid());
    }
    @Test
    void nullFields(){
        when(inputCardService.createInputCard(any(OutputCardDto.class))).thenReturn(outputCardDtoNullFields);
        when(validatorService.checkCategory(anyString(), anyString())).thenReturn(category);
        when(validatorService.checkSubcategory(anyString(), anyString())).thenReturn(subcategory);
        OutputCardDto restoredOutputCardDto = outputCardController.createInputCard(outputCardDtoNullFields);

        assertNotNull(restoredOutputCardDto);
        assertEquals("", restoredOutputCardDto.getUnit());
        assertEquals("", restoredOutputCardDto.getNote());
        assertEquals(0.0, restoredOutputCardDto.getDiscount());
    }

}
