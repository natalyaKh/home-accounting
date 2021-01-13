package smilyk.homeacc.service.bill;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.service.user.UserServiceImpl;

import java.util.Optional;

public class BillServiceImpl implements BillService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    BillRepository billRepository;

    @Override
    public BillDto createBill(BillDto billDto) {
        Bill bill = billDtoToBillEntity(billDto);
        billRepository.save(bill);
        LOGGER.info(BillConstants.BILL_WITH_NAME + bill.getBillName() + BillConstants.SAVED);
        return billDto;
    }

    @Override
    public BillDto changeMailBill(String billName) {
        Optional<Bill> mainBillOptional = billRepository.findByMainBill(true);
        if (mainBillOptional.isPresent()) {
            Bill oldMainBill = mainBillOptional.get();
            oldMainBill.setMainBill(false);
            billRepository.save(oldMainBill);
            LOGGER.info(BillConstants.BILL_WITH_NAME + oldMainBill.getBillName() + BillConstants.NOT_MAIN);
        }
        Optional<Bill> billOptional = billRepository.findByBillNameAndDeleted(billName, false);
        Bill bill = billOptional.get();
        bill.setMainBill(true);
        billRepository.save(bill);
        LOGGER.info(BillConstants.BILL_WITH_NAME + billName + BillConstants.MAIN);
        return billEntityToBillDto(bill);
    }

    private BillDto billEntityToBillDto(Bill bill) {
        return modelMapper.map(bill, BillDto.class);
    }

    private Bill billDtoToBillEntity(BillDto billDto) {
        return modelMapper.map(billDto, Bill.class);
    }
}



