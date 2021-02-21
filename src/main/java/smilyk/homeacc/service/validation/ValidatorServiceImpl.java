package smilyk.homeacc.service.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.CategorySubcategoryConstant;
import smilyk.homeacc.constants.ValidatorConstants;
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
import smilyk.homeacc.service.user.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserRepository userRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SubcategoryService subcategoryService;

    @Override
    public void checkUserUnique(String email) {
        LOGGER.info(ValidatorConstants.CHECK_USER_WITH_EMAIL + email);
        Optional<User> user = userRepository.findByEmailAndDeleted(email, false);
        if (!user.isEmpty()) {
            LOGGER.error(ValidatorConstants.NOT_UNIQUE_USER + email);
            throw new HomeaccException(ValidatorConstants.NOT_UNIQUE_USER + email);
        }
    }

    @Override
    public void checkUserExists(String userUuid) {
        LOGGER.info(ValidatorConstants.CHECK_USER_WITH_UUIDL + userUuid);
        Optional<User> user = userRepository.findByUserUuidAndDeleted(userUuid, false);
        if (user.isEmpty()) {
            LOGGER.error(ValidatorConstants.USER_WITH_UUID + userUuid + ValidatorConstants.NOT_FOUND);
            throw new HomeaccException(ValidatorConstants.NOT_UNIQUE_USER + userUuid);
        }
    }

    @Override
    public void checkMainBillsForDeleted(String billName) {
        LOGGER.info(ValidatorConstants.CHECK_BILLS_FOR_DELETED);
//        dont check bill per user - checked it before
        Optional<Bill> billOptional = billRepository.findByBillNameAndDeleted(billName, false);
        if (billOptional.get().getMainBill()) {
            LOGGER.error(ValidatorConstants.MAIN_BILL + billName + ValidatorConstants.CHANGE_MAIN_BILL);
            throw new HomeaccException(ValidatorConstants.MAIN_BILL + billName + ValidatorConstants.CHANGE_MAIN_BILL);
        }
    }

    @Override
    public Category checkCategory(String categoryName, String userUuid) {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryNameAndUserUuid(categoryName, userUuid);
        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        }
        CategoryDto categoryDto = CategoryDto.builder()
            .categoryName(categoryName)
            .userUuid(userUuid)
            .description("")
            .type(CategoryType.OUTPUT)
            .build();
        return categoryService.save(categoryDto);
    }

    @Override
    public Subcategory subCategory(String subcategoryName, String userUuid) {
        Optional<Subcategory> subcategoryOptional = subcategoryRepository
            .findBySubcategoryNameAndUserUuid(subcategoryName, userUuid);
        if (subcategoryOptional.isPresent()) {
            return subcategoryOptional.get();
        }
        SubcategoryDto subcategoryDto = SubcategoryDto.builder()
            .subCategoryName(subcategoryName)
            .userUuid(userUuid)
            .description("")
            .build();
        return subcategoryService.save(subcategoryDto);
    }

    @Override
    public void checkUniqueBill(String billName) {
        LOGGER.info(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName);
        Optional<Bill> bill = billRepository.findByBillNameAndDeleted(billName, false);
        if (!bill.isEmpty()) {
            LOGGER.error(ValidatorConstants.NOT_UNIQUE_BILL + billName);
            throw new HomeaccException(ValidatorConstants.NOT_UNIQUE_BILL + billName);
        }
    }

    @Override
    public void ckeckBill(String billName) {
        LOGGER.info(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName);
        Optional<Bill> bill = billRepository.findByBillNameAndDeleted(billName, false);
        if (bill.isEmpty()) {
            LOGGER.error(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName + ValidatorConstants.NOT_FOUND);
            throw new HomeaccException(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName +
                ValidatorConstants.NOT_FOUND);
        }
    }

    @Override
    public void checkBillByUser(String billName, String userUuid) {
        LOGGER.info(ValidatorConstants.CHECK_BILL_BY_BILL_NAME_AND_USER + billName + " " + userUuid);
        Optional<Bill> bill = billRepository.findByBillNameAndUserUuidAndDeleted(billName, userUuid, false);
        if (bill.isEmpty()) {
            LOGGER.error(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName + ValidatorConstants.FOR_USER + userUuid +
                ValidatorConstants.NOT_FOUND);
            throw new HomeaccException(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName +
                ValidatorConstants.FOR_USER + userUuid +
                ValidatorConstants.NOT_FOUND);
        }
    }

    @Override
    public void checkBillByUserAndCurrency(String billName, String userUuid, Currency currency) {
        LOGGER.info(ValidatorConstants.CHECK_BILL_BY_BILL_NAME_AND_USER + billName + " " + userUuid +
            ValidatorConstants.AND_CURRENCY + currency.name());

        Optional<Bill> bill = billRepository.findByBillNameAndUserUuidAndDeleted(billName, userUuid, false);
        if (bill.isEmpty()) {
            LOGGER.error(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName + ValidatorConstants.FOR_USER + userUuid +
                ValidatorConstants.NOT_FOUND);
            throw new HomeaccException(ValidatorConstants.CHECK_BILL_BY_BILL_NAME + billName +
                ValidatorConstants.FOR_USER + userUuid +
                ValidatorConstants.NOT_FOUND);
        }
        Currency billCurrencyRestored = bill.get().getCurrencyName();
        if (!billCurrencyRestored.equals(Currency.ALL)) {
            if (!billCurrencyRestored.equals(currency)
            ) {
                LOGGER.error( billName + ValidatorConstants.FOR_USER + userUuid +
                    ValidatorConstants.AND_CURRENCY + currency.name() +
                    ValidatorConstants.NOT_FOUND);
                throw new HomeaccException(billName +
                    ValidatorConstants.FOR_USER + userUuid +
                    ValidatorConstants.AND_CURRENCY + currency.name() +
                    ValidatorConstants.NOT_FOUND);
            }
        }
    }

    @SneakyThrows
    @Override
    public void checkCurrencyNameValid(String billsCurrency) {
        List<Currency> currency = Arrays.asList(Currency.values());

        try {
            Currency.valueOf(billsCurrency);
        } catch (Exception ex) {
            LOGGER.error(ValidatorConstants.CURRENCY_IS_WRONG + billsCurrency);

            throw new HomeaccException(ValidatorConstants.CURRENCY_IS_WRONG + ValidatorConstants.CHOOSE_CURRENCY +
                mapper.writeValueAsString(currency)
            );
//         } catch (JsonProcessingException e) {
//             LOGGER.error(e.getMessage());
//             throw new HomeaccException(e.getMessage());

        }
    }


    @Override
    public void checkMainBill(Boolean mainBill) {
        if (mainBill) {
            Optional<Bill> bill = billRepository.findByMainBill(true);
            if (!bill.isEmpty()) {
                LOGGER.info(ValidatorConstants.ONE_MAIN_BILL);
                throw new HomeaccException(ValidatorConstants.ONE_MAIN_BILL);
            }
        }
    }

}
