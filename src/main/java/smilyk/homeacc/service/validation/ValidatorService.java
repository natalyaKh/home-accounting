package smilyk.homeacc.service.validation;

import smilyk.homeacc.dto.BillDto;

public interface ValidatorService {
    void checkUserUnique(String email);
    void checkUniqueBill(String billName);
//    can be only one main bill per user
    void checkMainBill(Boolean mainBill);
    void ckeckBill(String billName);
}
