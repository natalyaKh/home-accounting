package smilyk.homeacc.service.bill;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import smilyk.homeacc.constants.BillConstants;
import smilyk.homeacc.dto.BillDto;
import smilyk.homeacc.dto.TransferResourcesBetweenBillsDto;
import smilyk.homeacc.dto.TransferResourcesResponseDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.utils.Utils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BillServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();
    private static final String USER_UUID = "1212";
    private static final String BILL_NAME = "BILL_NAME";
    private static final String BILL_UUID = "1111";
    private static final Currency All_CURRENCY_NAME = Currency.ALL;
    private static final Double SUMM_ISR = 100.0;
    private static final Double SUMM_UKR = 20.0;
    private static final Double SUMM_USA = 130.0;
    private Bill bill;
    private Bill mainBill;
    private Bill billCurrencyUsa;
    private Bill anotherUsersBill;
    private Bill billForDeleted;
    TransferResourcesBetweenBillsDto transferResourcesBetweenBillsDto;
    @InjectMocks
    BillServiceImpl billService;

    @Mock
    BillRepository billRepository;

    @Mock
    Utils utils;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
//        notMainBill
        bill = Bill.builder()
                .billName("bill")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.ALL)
                .mainBill(false)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
        billCurrencyUsa =  Bill.builder()
                .billName("billCurrencyUsa")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.USA)
                .mainBill(false)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
//        mainBill
        mainBill = Bill.builder()
                .billName("mainBill")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(All_CURRENCY_NAME)
                .mainBill(true)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
        anotherUsersBill = Bill.builder()
                .billName("mainBill")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(All_CURRENCY_NAME)
                .mainBill(true)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
        transferResourcesBetweenBillsDto = TransferResourcesBetweenBillsDto.builder()
                .currency(Currency.USA)
                .billNameFrom(bill.getBillName())
                .billNameTo(billCurrencyUsa.getBillName())
                .sum(100.0)
                .build();
        billForDeleted = Bill.builder()
                .billName("bill for deleted")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.ALL)
                .mainBill(false)
                .description("")
                .sumIsr(0.00)
                .sumUkr(0.00)
                .sumUsa(0.00)
                .deleted(false)
                .build();

    }

    @Test
    void testCreateBill() {

        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        when(billRepository.save(any(Bill.class))).thenReturn(bill);

        BillDto billDto = modelMapper.map(bill, BillDto.class);
        BillDto storedBillDto = billService.createBill(billDto);

        assertNotNull(storedBillDto);
        assertEquals(bill.getBillName(), storedBillDto.getBillName());
        assertNotNull(storedBillDto.getBillUuid());
        assertNotNull(storedBillDto.getUserUuid());

        verify(utils, times(1)).generateUserUuid();
        verify(billRepository, times(1)).save(any(Bill.class));

    }

    @Test
    void testChangeMailBill() {
        Optional<Bill> returnCacheMainBuild = Optional.of(mainBill);
        Optional<Bill> returnCacheNotMainBuild = Optional.of(bill);

        when(billRepository.findByMainBill(eq(true))).thenReturn(returnCacheMainBuild);
        when(billRepository.findByBillNameAndDeleted(anyString(), eq(false))).thenReturn(returnCacheNotMainBuild);
        BillDto billDto = billService.changeMailBill(bill.getBillName());

        assertNotNull(billDto);
        assertEquals(true, billDto.getMainBill());

        verify(billRepository, times(2)).save(any(Bill.class));
    }

    @Test
    void testGetBillByBillNameWithException() {
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class,
                () -> billService.getBillByBillName(bill.getBillName(), bill.getUserUuid()));

    }


    @Test
    void testGetBillByBillName() {

        Optional<Bill> returnCacheNotMainBuild = Optional.of(bill);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(returnCacheNotMainBuild);

        BillDto billDto = billService.getBillByBillName(bill.getBillName(), bill.getUserUuid());

        assertNotNull(billDto);
        assertEquals(billDto.getBillName(), bill.getBillName());
        assertEquals(billDto.getBillUuid(), bill.getBillUuid());
        assertEquals(billDto.getDescription(), bill.getDescription());
        assertEquals(billDto.getMainBill(), bill.getMainBill());
        assertEquals(billDto.getUserUuid(), bill.getUserUuid());
        assertEquals(billDto.getCurrencyName(), bill.getCurrencyName());
        assertEquals(billDto.getSumIsr(), bill.getSumIsr());
        assertEquals(billDto.getSumUkr(), bill.getSumUkr());
        assertEquals(billDto.getSumUsa(), bill.getSumUsa());


    }

    @Test
    void getAllBillsByUser() {
        List<Bill> billList = Arrays.asList(bill, mainBill);

        when(billRepository.findAllByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(billList);

        List<BillDto> billDtoList = billService.getAllBillsByUser(USER_UUID);

        assertEquals(2, billDtoList.size());

    }

    @Test
    void getAllBillsByUserLogger() {
        List<Bill> billList = Arrays.asList();

        when(billRepository.findAllByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(billList);

        List<BillDto> billDtoList = billService.getAllBillsByUser(USER_UUID);

        assertEquals(0, billDtoList.size());


    }


    @Test
    void getAllBillsByUserUuidAndCurrencyAllCurrency() {
        List<Bill> billList = Arrays.asList(bill, billCurrencyUsa, mainBill);

        when(billRepository.findAllByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(billList);

        List<BillDto> billDtoList = billService.getAllBillsByUserUuidAndCurrency(USER_UUID, "ALL");

        assertNotNull(billDtoList);
        assertEquals(2, billDtoList.size());
    }

    @Test
    void getAllBillsByUserUuidAndCurrencyAllCurrencyNotFoundException() {
        List<Bill> billList = Arrays.asList();
        when(billRepository.findAllByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(billList);

        List<BillDto> returnedBillList = billService.getAllBillsByUserUuidAndCurrency(bill.getBillName(),
                bill.getUserUuid());

        assertEquals(0, returnedBillList.size());

    }
    @Test
    void getAllBillsByUserUuidAndCurrencyUSACurrency() {
        List<Bill> billList = Arrays.asList(bill, billCurrencyUsa, mainBill);

        when(billRepository.findAllByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(billList);

        List<BillDto> billDtoList = billService.getAllBillsByUserUuidAndCurrency(USER_UUID, "USA");

        assertNotNull(billDtoList);
        assertEquals(3, billDtoList.size());
    }

    @Test
    void transferResourcesCurrencyUsa() {
        Optional<Bill> returnCacheBillFrom = Optional.of(bill);
        Optional<Bill> returnCacheBillTo = Optional.of(billCurrencyUsa);

        when(billRepository.findByBillNameAndDeleted(anyString(), anyBoolean()))
                .thenReturn(returnCacheBillFrom, returnCacheBillTo);

        when(billRepository.save(any(Bill.class))).thenReturn(bill, billCurrencyUsa);

        TransferResourcesResponseDto transferResources =
                billService.transferResources(transferResourcesBetweenBillsDto);

        assertNotNull(transferResources);
        assertEquals(30, transferResources.getResponseBillDtoList().get(0).getSumUsa());
        assertEquals(230, transferResources.getResponseBillDtoList().get(1).getSumUsa());
//
//
        verify(billRepository, times(2)).save(any(Bill.class));
    }

    @Test
    void transferResourcesCurrencyIsr() {
        Bill billCurrencyIsr =  Bill.builder()
                .billName("billCurrencyUsa")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.ISR)
                .mainBill(false)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
        transferResourcesBetweenBillsDto = TransferResourcesBetweenBillsDto.builder()
                .currency(Currency.ISR)
                .billNameFrom(bill.getBillName())
                .billNameTo(billCurrencyIsr.getBillName())
                .sum(100.0)
                .build();

        Optional<Bill> returnCacheBillFrom = Optional.of(bill);
        Optional<Bill> returnCacheBillTo = Optional.of(billCurrencyIsr);

        when(billRepository.findByBillNameAndDeleted(anyString(), anyBoolean()))
                .thenReturn(returnCacheBillFrom, returnCacheBillTo);
        when(billRepository.save(any(Bill.class))).thenReturn(bill, billCurrencyIsr);

        TransferResourcesResponseDto transferResources =
                billService.transferResources(transferResourcesBetweenBillsDto);

        assertNotNull(transferResources);
        assertEquals(0, transferResources.getResponseBillDtoList().get(0).getSumIsr());
        assertEquals(200, transferResources.getResponseBillDtoList().get(1).getSumIsr());

        verify(billRepository, times(2)).save(any(Bill.class));
    }

    @Test
    void transferResourcesCurrencyUkr() {
        Bill billCurrencyUkr =  Bill.builder()
                .billName("billCurrencyUkr")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.UKR)
                .mainBill(false)
                .description("")
                .sumIsr(SUMM_ISR)
                .sumUkr(SUMM_UKR)
                .sumUsa(SUMM_USA)
                .deleted(false)
                .build();
        transferResourcesBetweenBillsDto = TransferResourcesBetweenBillsDto.builder()
                .currency(Currency.UKR)
                .billNameFrom(bill.getBillName())
                .billNameTo(billCurrencyUkr.getBillName())
                .sum(10.0)
                .build();

        Optional<Bill> returnCacheBillFrom = Optional.of(bill);
        Optional<Bill> returnCacheBillTo = Optional.of(billCurrencyUkr);

        when(billRepository.findByBillNameAndDeleted(anyString(), anyBoolean()))
                .thenReturn(returnCacheBillFrom, returnCacheBillTo);

        when(billRepository.save(any(Bill.class))).thenReturn(bill, billCurrencyUkr);

        TransferResourcesResponseDto transferResources =
                billService.transferResources(transferResourcesBetweenBillsDto);

        assertNotNull(transferResources);
        assertEquals(10, transferResources.getResponseBillDtoList().get(0).getSumUkr());
        assertEquals(30, transferResources.getResponseBillDtoList().get(1).getSumUkr());
//
//
        verify(billRepository, times(2)).save(any(Bill.class));
    }

    @Test
    void testDeleteBill() {

        Optional<Bill> returnCacheBill = Optional.of(billForDeleted);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(returnCacheBill);

        when(billRepository.save(any(Bill.class))).thenReturn(billForDeleted);
        billService.deleteBill(billForDeleted.getBillName(), billForDeleted.getUserUuid());

        assertTrue(billForDeleted.isDeleted());
    }

    @Test
    void testDeleteBillExceptionIsr() {
        Optional<Bill> returnCacheBill = Optional.of(bill);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(returnCacheBill);

        assertThrows(HomeaccException.class, () -> billService.deleteBill(
                billForDeleted.getBillName(), billForDeleted.getUserUuid()));

    }

    @Test
    void testDeleteBillExceptionUkr() {
        Bill billCurrencyUkr =  Bill.builder()
                .billName("billCurrencyUkr")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.UKR)
                .mainBill(false)
                .description("")
                .sumIsr(0.0)
                .sumUkr(SUMM_UKR)
                .sumUsa(0.0)
                .deleted(false)
                .build();
        Optional<Bill> returnCacheBill = Optional.of(billCurrencyUkr);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(returnCacheBill);

        assertThrows(HomeaccException.class, () -> billService.deleteBill(
                billForDeleted.getBillName(), billForDeleted.getUserUuid()));

    }

    @Test
    void testDeleteBillExceptionUsa() {
        Bill billCurrencyUsa =  Bill.builder()
                .billName("billCurrencyUkr")
                .billUuid(BILL_UUID)
                .userUuid(USER_UUID)
                .currencyName(Currency.UKR)
                .mainBill(false)
                .description("")
                .sumIsr(0.0)
                .sumUkr(0.0)
                .sumUsa(10.0)
                .deleted(false)
                .build();
        Optional<Bill> returnCacheBill = Optional.of(billCurrencyUsa);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(returnCacheBill);

        assertThrows(HomeaccException.class, () -> billService.deleteBill(
                billForDeleted.getBillName(), billForDeleted.getUserUuid()));
    }

    @Test
    void testDeleteBillNotFoundBillException() {

        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(), eq(false)))
                .thenReturn(Optional.empty());

        assertThrows(HomeaccException.class, () -> billService.deleteBill(
                billForDeleted.getBillName(), billForDeleted.getUserUuid()));

    }
}