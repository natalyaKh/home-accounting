package smilyk.homeacc.service.bill;


import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.dto.TransferResourcesBetweenBillsDto;
import smilyk.homeacc.dto.TransferResourcesResponseDto;

import java.util.List;

public interface BillService {

    BillDto createBill(BillDto billDto);
    BillDto changeMailBill(String billName);
    BillDto getBillByBillName(String billName, String userUuid);
    List<BillDto> getAllBillsByUser(String userUuid);
    void deleteBill(String billName, String userUuid);
    List<BillDto> getAllBillsByUserUuidAndCurrency(String userUuid, String billsCurrency);
    TransferResourcesResponseDto transferResources(TransferResourcesBetweenBillsDto transferDto);
//    for validation
    Boolean getBillByNameForValidation(String billName, String userUuid);
}
