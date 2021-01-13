package smilyk.homeacc.service.bill;


import smilyk.homeacc.dto.BillDto;

import java.util.List;

public interface BillService {

    BillDto createBill(BillDto billDto);
    BillDto changeMailBill(String billName);
    BillDto getBillByBillName(String billName);
    List<BillDto> getAllBillsByUser(String userUuid);
}
