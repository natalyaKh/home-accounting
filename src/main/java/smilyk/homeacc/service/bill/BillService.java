package smilyk.homeacc.service.bill;


import smilyk.homeacc.dto.BillDto;

public interface BillService {

    BillDto createBill(BillDto billDto);
    BillDto changeMailBill(String billName);
}
