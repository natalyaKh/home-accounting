package smilyk.homeacc.service.bill;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.dto.TransferResourcesBetweenBillsDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.service.user.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        List<Bill> billsList = billRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if (billsList.size() == 0) {
            LOGGER.info(BillConstants.BILLS_LIST + BillConstants.FOR_USER +
                    userUuid + BillConstants.IS_EMPTY);
            return ListBillEntityToListBillDto(billsList);
        }
        List<BillDto> listBillDto = ListBillEntityToListBillDto(billsList);
        LOGGER.info(BillConstants.BILLS_LIST);
        return makeMainBillFirst(listBillDto);
    }
    @Override
    public List<BillDto> getAllBillsByUserUuidAndCurrency(String userUuid, String billsCurrency) {
        List<Bill> billsList = billRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if (billsList.size() == 0) {
            LOGGER.info(BillConstants.BILLS_LIST + BillConstants.FOR_USER +
                    userUuid + BillConstants.IS_EMPTY);
            return ListBillEntityToListBillDto(billsList);
        }
        List<BillDto> listBillDto = billsList.stream().map(this::billEntityToBillDto)
                .filter(b-> b.getCurrencyName().name().equals(billsCurrency)||
                        b.getCurrencyName().name().equals(Currency.ALL))
                .collect(Collectors.toList());
        LOGGER.info(BillConstants.BILLS_LIST);
        return listBillDto;
    }

    @Override
    public TransferResourcesBetweenBillsDto transferResources(TransferResourcesBetweenBillsDto transferDto) {
        Optional<Bill> billFromOptional = billRepository.findByBillNameAndDeleted(transferDto.getBillNameFrom(), false);
        Bill billFrom = billFromOptional.get();
        Optional<Bill> billToOptional = billRepository.findByBillNameAndDeleted(transferDto.getBillNameTo(), false);
        Bill billTo = billToOptional.get();
        Currency currency = transferDto.getCurrency();
       if(currency.equals(Currency.ISR) || currency.equals(Currency.ALL)){
           changeSumByIsrShekel(billFrom, billTo, transferDto.getSum());
       }
        if(currency.equals(Currency.UKR) || currency.equals(Currency.ALL)){
            changeSumByUkrHryvna(billFrom, billTo, transferDto.getSum());
        }
        if(currency.equals(Currency.USA) || currency.equals(Currency.ALL)){
            changeSumByUsaDollar(billFrom, billTo, transferDto.getSum());
        }
        return transferDto;
    }

    private void changeSumByUsaDollar(Bill billFrom, Bill billTo, Double sum) {
        Double startSumFrom = billFrom.getSumUsa();
        Double newSumFrom = startSumFrom - sum;
        billFrom.setSumUsa(newSumFrom);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_USA +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        Double startSumTo = billTo.getSumUsa();
        Double newSumTo = startSumTo + sum;
        billTo.setSumUsa(newSumTo);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_USA +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        billRepository.save(billFrom);
        billRepository.save(billTo);
    }

    private void changeSumByUkrHryvna(Bill billFrom, Bill billTo, Double sum) {
        Double startSumFrom = billFrom.getSumUkr();
        Double newSumFrom = startSumFrom - sum;
        billFrom.setSumUkr(newSumFrom);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_UKR +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        Double startSumTo = billTo.getSumUkr();
        Double newSumTo = startSumTo + sum;
        billTo.setSumUkr(newSumTo);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_UKR +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        billRepository.save(billFrom);
        billRepository.save(billTo);
    }

    private void changeSumByIsrShekel(Bill billFrom, Bill billTo, Double sum) {
        Double startSumFrom = billFrom.getSumIsr();
        Double newSumFrom = startSumFrom - sum;
        billFrom.setSumIsr(newSumFrom);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_ISR +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        Double startSumTo = billTo.getSumIsr();
        Double newSumTo = startSumTo + sum;
        billTo.setSumIsr(newSumTo);
        LOGGER.info(BillConstants.CHANGED + BillConstants.SUM_ISR +
                BillConstants.FROM + startSumFrom + BillConstants.TO + newSumFrom
                + BillConstants.FOR + BillConstants.BILL_WITH_NAME + billFrom.getBillName());
        billRepository.save(billFrom);
        billRepository.save(billTo);
    }

    private List<BillDto> ListBillEntityToListBillDto(List<Bill> billsList) {
        return billsList.stream().map(this::billEntityToBillDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBill(String billName, String userUuid) {
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



