package smilyk.homeacc.service.bill;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.constants.UserConstants;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.dto.TransferResourcesBetweenBillsDto;
import smilyk.homeacc.dto.TransferResourcesResponseDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private static final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    BillRepository billRepository;

    @Autowired
    Utils utils;

    @Override
//    checked
    public BillDto createBill(BillDto billDto) {
        Bill bill = billDtoToBillEntity(billDto);
        String billUuid = utils.generateUserUuid().toString();
        bill.setBillUuid(billUuid);
        billRepository.save(bill);
        billDto.setBillUuid(billUuid);
        LOGGER.info(BillConstants.BILL_WITH_NAME + bill.getBillName() + BillConstants.SAVED);
        return billDto;
    }

    @Override
//    checked
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
//    checked
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
//    checked
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
//    checked
    public List<BillDto> getAllBillsByUserUuidAndCurrency(String userUuid, String billsCurrency) {
        List<Bill> billsList = billRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if (billsList.size() == 0) {
            LOGGER.info(BillConstants.BILLS_LIST + BillConstants.FOR_USER +
                    userUuid + BillConstants.IS_EMPTY);
            return ListBillEntityToListBillDto(billsList);
        }
        List<BillDto> listBillDto = billsList.stream().map(this::billEntityToBillDto)
                .filter(b -> b.getCurrencyName().name().equals(billsCurrency) ||
                        b.getCurrencyName().name().equals(Currency.ALL.name()))
                .collect(Collectors.toList());
        LOGGER.info(BillConstants.BILLS_LIST);
        return listBillDto;
    }

    @Override
    public TransferResourcesResponseDto transferResources(TransferResourcesBetweenBillsDto transferDto) {
        Optional<Bill> billFromOptional = billRepository.findByBillNameAndDeleted(transferDto.getBillNameFrom(), false);
        Bill billFrom = billFromOptional.get();
        Optional<Bill> billToOptional = billRepository.findByBillNameAndDeleted(transferDto.getBillNameTo(), false);
        Bill billTo = billToOptional.get();
        Currency currency = transferDto.getCurrency();
        TransferResourcesResponseDto resourcesResponseDto = TransferResourcesResponseDto.builder().build();
        if (currency.equals(Currency.ISR) || currency.equals(Currency.ALL)) {
            resourcesResponseDto = changeSumByIsrShekel(billFrom, billTo, transferDto.getSum());
        }
        if (currency.equals(Currency.UKR) || currency.equals(Currency.ALL)) {
            resourcesResponseDto = changeSumByUkrHryvna(billFrom, billTo, transferDto.getSum());
        }
        if (currency.equals(Currency.USA) || currency.equals(Currency.ALL)) {
            resourcesResponseDto = changeSumByUsaDollar(billFrom, billTo, transferDto.getSum());
        }
        return resourcesResponseDto;
    }

    private TransferResourcesResponseDto changeSumByUsaDollar(Bill billFrom, Bill billTo, Double sum) {
//        TODO
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
        billFrom = billRepository.save(billFrom);
        billTo = billRepository.save(billTo);
        return TransferResourcesResponseDto.builder()
                .responseBillDtoList(Arrays.asList(billEntityToBillDto(billFrom), billEntityToBillDto(billTo)))
                .build();
    }

    private TransferResourcesResponseDto changeSumByUkrHryvna(Bill billFrom, Bill billTo, Double sum) {
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
        billFrom = billRepository.save(billFrom);
        billTo = billRepository.save(billTo);
        return TransferResourcesResponseDto.builder()
                .responseBillDtoList(Arrays.asList(billEntityToBillDto(billFrom), billEntityToBillDto(billTo)))
                .build();
    }

    private TransferResourcesResponseDto changeSumByIsrShekel(Bill billFrom, Bill billTo, Double sum) {
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
        billFrom = billRepository.save(billFrom);
        billTo = billRepository.save(billTo);
        return TransferResourcesResponseDto.builder()
                .responseBillDtoList(Arrays.asList(billEntityToBillDto(billFrom), billEntityToBillDto(billTo)))
                .build();
    }

    private List<BillDto> ListBillEntityToListBillDto(List<Bill> billsList) {
        return billsList.stream().map(this::billEntityToBillDto)
                .collect(Collectors.toList());
    }

    @Override
//    checked
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

    @Override
    public Boolean getBillByNameForValidation(String billName, String userUuid) {
        Optional<Bill> billOptional = billRepository.findByBillNameAndUserUuidAndDeleted(
                billName, userUuid, false
        );
        if (billOptional.isPresent()) {
            LOGGER.info(BillConstants.BILL_WITH_NAME + billName
                    + BillConstants.FOR_USER + userUuid + BillConstants.EXISTS);
            return true;
        } else {
            return false;
        }
    }

        private List<BillDto> makeMainBillFirst (List < BillDto > listBillDto) {

            Optional<BillDto> mainBillDtoOptional = listBillDto.stream().filter(b -> b.getMainBill())
                    .findAny();
            if(mainBillDtoOptional.isPresent()){
                BillDto mainBillDto = mainBillDtoOptional.get();
                listBillDto.remove(mainBillDto);
                listBillDto.add(0, mainBillDto);
                return listBillDto;
            }else{
                return listBillDto;
            }
        }

        private BillDto billEntityToBillDto (Bill bill){
            return modelMapper.map(bill, BillDto.class);
        }

        private Bill billDtoToBillEntity (BillDto billDto){
            return modelMapper.map(billDto, Bill.class);
        }
    }



