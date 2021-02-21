package smilyk.homeacc.service.validation;

import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Category;
import smilyk.homeacc.model.Subcategory;

import javax.validation.constraints.NotNull;

public interface ValidatorService {
    void checkUserUnique(String email);
    void checkUniqueBill(String billName);
//    can be only one main bill per user
    void checkMainBill(Boolean mainBill);
    void ckeckBill(String billName);

    void checkBillByUser(String billNameFrom, String userUuid);
    void checkBillByUserAndCurrency(String billNameFrom, String userUuid, Currency currency);

    void checkCurrencyNameValid(String billsCurrency);

    void checkUserExists(String userUuid);

    void checkMainBillsForDeleted(String billName);

    Category checkCategory(String categoryUuid, String userUuid);

    Subcategory checkSubcategory(String subCategoryUuid, String userUuid);
}
