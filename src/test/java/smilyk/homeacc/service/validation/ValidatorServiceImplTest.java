package smilyk.homeacc.service.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smilyk.homeacc.dto.CategoryDto;
import smilyk.homeacc.dto.SubcategoryDto;
import smilyk.homeacc.enums.CategoryType;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.CategoryRepository;
import smilyk.homeacc.repo.SubcategoryRepository;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.category.CategoryService;
import smilyk.homeacc.service.subcategory.SubcategoryService;
import smilyk.homeacc.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class ValidatorServiceImplTest {

    private static final String USER_UUID = "1111";
    private static final String ENCRYPTED_PASSWORD = "1234";
    private static final String EMAIL_VERIFICATION_TOKEN = "12345";
    private static final String USER_LAST_NAME = "UserLastName";
    private static final String USER_FIRST_NAME = "UserFirstName";
    private static final String EMAIL = "mail@mail.com";

    private static final String BILL_NAME = "BILL_NAME";
    private static final String BILL_UUID = "1111";
    private static final Currency All_CURRENCY_NAME = Currency.ALL;
    private static final Double SUMM_ISR = 100.0;
    private static final Double SUMM_UKR = 20.0;
    private static final Double SUMM_USA = 130.0;
    private static final String CATEGORY_NAME = "Products";
    private static final String CATEGORY_UUID = "5555";
    private static final String SUBCATEGORY_UUID = "6666";
    private static final String SUBCATEGORY_NAME = "dress";
    private User user;
    private Bill bill;
    private Bill mainBill;
    private Bill billUsa;
    private Category category;
    private Subcategory subcategory;


    Optional<Bill> returnCacheValue;
    Optional<User> returnCacheValueUser;

    @InjectMocks
    ValidatorServiceImpl validatorService;
    @Mock
    UserRepository userRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    SubcategoryRepository subcategoryRepository;
    @Mock
    CategoryService categoryService;
    @Mock
    SubcategoryService subcategoryService;
    @Mock
    Utils utils;
    @Mock
    BillRepository billRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        user = User.builder()
            .firstName(USER_FIRST_NAME)
            .lastName(USER_LAST_NAME)
            .encryptedPassword(ENCRYPTED_PASSWORD)
            .deleted(false)
            .email(EMAIL)
            .userUuid(USER_UUID)
            .emailVerificationStatus(false)
            .emailVerificationToken(EMAIL_VERIFICATION_TOKEN)
            .build();
        bill = Bill.builder()
            .billName("bill")
            .billUuid(BILL_UUID)
            .userUuid(USER_UUID)
            .currencyName(Currency.ALL)
            .mainBill(false)
            .description("")
            .sumIsr(SUMM_ISR)
            .sumUkr(SUMM_UKR)
            .sumUsa(SUMM_USA)
            .deleted(false)
            .build();
//        mainBill
        mainBill = Bill.builder()
            .billName("mainBill")
            .billUuid(BILL_UUID)
            .userUuid(USER_UUID)
            .currencyName(All_CURRENCY_NAME)
            .mainBill(true)
            .description("")
            .sumIsr(SUMM_ISR)
            .sumUkr(SUMM_UKR)
            .sumUsa(SUMM_USA)
            .deleted(false)
            .build();
        billUsa = Bill.builder()
            .billName("mainBill")
            .billUuid(BILL_UUID)
            .userUuid(USER_UUID)
            .currencyName(Currency.USA)
            .mainBill(true)
            .description("")
            .sumIsr(SUMM_ISR)
            .sumUkr(SUMM_UKR)
            .sumUsa(SUMM_USA)
            .deleted(false)
            .build();
        category = Category.builder()
            .categoryName(CATEGORY_NAME)
            .categoryUuid(CATEGORY_UUID)
            .userUuid(USER_UUID)
            .deleted(false)
            .description("")
            .type(CategoryType.OUTPUT)
            .build();
        subcategory = Subcategory.builder()
            .subcategoryUuid(SUBCATEGORY_UUID)
            .subcategoryName(SUBCATEGORY_NAME)
            .deleted(false)
            .userUuid("2222")
            .description("this is subcategory")
            .build();
        returnCacheValue = Optional.of(bill);
        returnCacheValueUser = Optional.of(user);

    }

    @Test
    void testCheckUserUniqueNotValid() {
        when(userRepository.findByEmailAndDeleted(anyString(), eq(false)))
            .thenReturn(returnCacheValueUser);
        assertThrows(HomeaccException.class, () -> validatorService.checkUserUnique(USER_UUID));
    }

    @Test
    void testCheckUserUniqueValid() {
        when(userRepository.findByEmailAndDeleted(anyString(), eq(false)))
            .thenReturn(Optional.empty());
        validatorService.checkUserUnique("m@mail.com");

    }

    @Test
    void testCheckUserExistsNotValid() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> validatorService.checkUserExists(USER_UUID));
    }

    @Test
    void testCheckUserExistsValid() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValueUser);
        validatorService.checkUserExists(USER_UUID);
    }

    @Test
    void testCheckMainBillsForDeletedNotValid() {
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValue);
        validatorService.checkMainBillsForDeleted(bill.getBillName());
    }

    @Test
    void testCheckMainBillsForDeletedValid() {
        returnCacheValue = Optional.of(mainBill);
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValue);
        assertThrows(HomeaccException.class, () -> validatorService.checkMainBillsForDeleted(BILL_NAME));
    }

    @Test
    void testCheckUniqueBillValid() {
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(Optional.empty());
        validatorService.checkUniqueBill(BILL_NAME);
    }

    @Test
    void testCheckUniqueBillNotValid() {
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValue);
        assertThrows(HomeaccException.class, () -> validatorService.checkUniqueBill(BILL_NAME));
    }

    @Test
    void testCkeckBillValid() {
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValue);
        validatorService.checkBill(BILL_NAME);
    }

    @Test
    void testCheckBillNotValid() {
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> validatorService.checkBill(BILL_NAME));
    }

    @Test
    void billCurrencyNotValid(){
        returnCacheValue = Optional.of(mainBill);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
            .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> validatorService.checkBillByUserAndCurrency(BILL_NAME, USER_UUID, Currency.USA));
    }

    @Test
    void testCheckBillByUserValid() {
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
            .thenReturn(returnCacheValue);
        validatorService.checkBillByUser(BILL_NAME, USER_UUID);
    }

    @Test
    void testCheckBillByUserNotValid() {
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
            .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> validatorService.checkBillByUser(BILL_NAME, USER_UUID));
    }

    @Test
    void testCheckBillByUserAndCurrencyValid() {
        when(billRepository.findByBillNameAndUserUuidAndDeleted(
            anyString(), anyString(), eq(false)
        )).thenReturn(returnCacheValue);
        validatorService.checkBillByUserAndCurrency(BILL_NAME, USER_UUID, Currency.ALL);

    }

    @Test
    void testCheckBillByUserAndCurrencyNotValid() {
        when(billRepository.findByBillNameAndUserUuidAndDeletedAndCurrencyName(
            anyString(), anyString(), eq(false), anyString()
        )).thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> validatorService.checkBillByUserAndCurrency(BILL_NAME, USER_UUID,
            Currency.ALL));
    }

    @Test
    void testCheckWrongCurrency(){
        returnCacheValue = Optional.of(billUsa);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(
           anyString(), anyString(), eq(false)
        )).thenReturn(returnCacheValue);
        assertThrows(HomeaccException.class, () -> validatorService.checkBillByUserAndCurrency(BILL_NAME, USER_UUID,
            Currency.UKR));

    }

    @Test
    void testCheckCurrencyNameValid() {
        List<Currency> currency = Arrays.asList(Currency.values());
        validatorService.checkCurrencyNameValid(Currency.ALL.name());
        validatorService.checkCurrencyNameValid(Currency.USA.name());
        validatorService.checkCurrencyNameValid(Currency.ISR.name());
        validatorService.checkCurrencyNameValid(Currency.UKR.name());
    }

    @Test
    void testCheckCurrencyNameValidWithException() {
        List<Currency> currency = Arrays.asList(Currency.values());
        assertThrows(HomeaccException.class, () -> validatorService.checkCurrencyNameValid("Other"));
    }

    @Test
    void testCheckMainBillValid() {
        returnCacheValue = Optional.of(mainBill);
        when(billRepository.findByMainBill(eq(true))).thenReturn(Optional.empty());
        validatorService.checkMainBill(true);

    }

    @Test
    void testCheckMainBillNotValid() {
        when(billRepository.findByMainBill(eq(true))).thenReturn(returnCacheValue);
        assertThrows(HomeaccException.class, () -> validatorService.checkMainBill(true));
    }

    @Test
    void testCheckCategory() {
        Optional<Category> restoredCategory = Optional.of(category);
        when(categoryRepository.findByCategoryNameAndUserUuid(anyString(), anyString())).thenReturn(restoredCategory);
        Category returnedCategory = validatorService.checkCategory(CATEGORY_NAME, USER_UUID);

        assertNotNull(returnedCategory);
    }

    @Test
    void testCheckNewCategory() {
        when(categoryRepository.findByCategoryNameAndUserUuid(anyString(), anyString())).thenReturn(Optional.empty());
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        when(categoryService.save(any(CategoryDto.class))).thenReturn(category);
        Category returnedCategory = validatorService.checkCategory("newCategory", USER_UUID);

        assertNotNull(returnedCategory);
    }

    @Test
    void testCheckCSubcategory() {
        Optional<Subcategory> restoredSubcategory = Optional.of(subcategory);
        when(subcategoryRepository.findBySubcategoryNameAndUserUuid(anyString(), anyString()))
            .thenReturn(restoredSubcategory);
        Subcategory returnedSubcategory = validatorService.checkSubcategory(SUBCATEGORY_NAME, USER_UUID);

        assertNotNull(returnedSubcategory);
    }

    @Test
    void testCheckNewSubcategory() {
        when(subcategoryRepository.findBySubcategoryNameAndUserUuid(anyString(), anyString()))
            .thenReturn(Optional.empty());
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        when(subcategoryService.save(any(SubcategoryDto.class))).thenReturn(subcategory);
        Subcategory returnedSubcategory = validatorService.checkSubcategory("subc", USER_UUID);

        assertNotNull(returnedSubcategory);
    }
}
