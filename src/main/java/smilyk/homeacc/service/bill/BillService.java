package smilyk.homeacc.service.bill;


import org.springframework.stereotype.Service;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.dto.TransferResourcesBetweenBillsDto;

import java.util.List;

public interface BillService {

    BillDto createBill(BillDto billDto);
    BillDto changeMailBill(String billName);
    BillDto getBillByBillName(String billName, String userUuid);
    List<BillDto> getAllBillsByUser(String userUuid);
    void deleteBill(String billName, String userUuid);
    List<BillDto> getAllBillsByUserUuidAndCurrency(String userUuid, String billsCurrency);
    TransferResourcesBetweenBillsDto transferResources(TransferResourcesBetweenBillsDto transferDto);
}
