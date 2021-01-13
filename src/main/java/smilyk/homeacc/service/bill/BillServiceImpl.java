package smilyk.homeacc.service.bill;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.service.user.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public BillDto getBillByBillName(String billName, String userUuid) {
        Optional<Bill> billOptional = billRepository.findByBillNameAndUserUuidAndDeleted(billName, userUuid, false);
        if (!billOptional.isPresent()) {
            LOGGER.info(BillConstants.BILL_WITH_NAME + billName + BillConstants.NOT_FOUND);
            throw new HomeaccException(BillConstants.BILL_WITH_NAME + billName + BillConstants.NOT_FOUND);
        }
        Bill billEntity = billOptional.get();
        BillDto billDto = billEntityToBillDto(billEntity);
        LOGGER.info(BillConstants.BILL_WITH_NAME + billEntity.getBillName() + BillConstants.FOUND);
        return billDto;
    }

    @Override
    public List<BillDto> getAllBillsByUser(String userUuid) {
        List<Bill> billsList = billRepository.findAllByUserUuid(userUuid);
        if (billsList.size() == 0) {
            LOGGER.info(BillConstants.BILLS_LIST + BillConstants.FOR_USER +
                    userUuid + BillConstants.IS_EMPTY);
        }
        List<BillDto> listBillDto = billsList.stream().map(this::billEntityToBillDto)
                .collect(Collectors.toList());
        LOGGER.info(BillConstants.BILLS_LIST);
        return makeMainBillFirst(listBillDto);
    }

    @Override
    public void deleteUser(String billName, String userUuid) {
        Optional<Bill> optionalBill = billRepository.findByBillNameAndUserUuidAndDeleted(
                billName, userUuid, false);
        if (!optionalBill.isPresent()) {
            LOGGER.info(BillConstants.BILL_WITH_NAME + billName +
                    BillConstants.FOR_USER + userUuid + BillConstants.NOT_FOUND);
            throw new HomeaccException(BillConstants.BILL_WITH_NAME + billName +
                    BillConstants.FOR_USER + userUuid + BillConstants.NOT_FOUND);
        }
        Bill bill = optionalBill.get();
        if (bill.getSumIsr() != 0.0) {
            LOGGER.error(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_ISR +
                    BillConstants.NOT_NULL);
            throw new HomeaccException(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_ISR +
                    BillConstants.NOT_NULL);
        }
        if (bill.getSumUkr() != 0.0) {
            LOGGER.error(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_UKR +
                    BillConstants.NOT_NULL);
            throw new HomeaccException(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_UKR +
                    BillConstants.NOT_NULL);
        }
        if (bill.getSumUsa() != 0.0) {
            LOGGER.error(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_USA +
                    BillConstants.NOT_NULL);
            throw new HomeaccException(BillConstants.CAN_NOT_DELETE + BillConstants.SUM_USA +
                    BillConstants.NOT_NULL);
        }

        bill.setDeleted(true);
        billRepository.save(bill);
        LOGGER.info(BillConstants.BILL_WITH_NAME + billName + BillConstants.DELETED);
    }

    private List<BillDto> makeMainBillFirst(List<BillDto> listBillDto) {
        BillDto mainBillDto = listBillDto.stream().filter(b -> b.getMainBill() == true)
                .findAny().get();
        listBillDto.remove(mainBillDto);
        listBillDto.add(0, mainBillDto);
        return listBillDto;
    }

    private BillDto billEntityToBillDto(Bill bill) {
        return modelMapper.map(bill, BillDto.class);
    }

    private Bill billDtoToBillEntity(BillDto billDto) {
        return modelMapper.map(billDto, Bill.class);
    }
}



