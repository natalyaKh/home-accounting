package smilyk.homeacc.service.outputCard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import smilyk.homeacc.dto.OutputCardDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.OutputCard;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.InputCardRepository;
import smilyk.homeacc.utils.Utils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OutputCardServiceImplTest {

    private static final String BILL_UUID = "1111";
    private static final String USER_UUID = "3333";
    ModelMapper modelMapper = new ModelMapper();
    private OutputCard outputCardUsa;
    private OutputCard outputCardUkr;
    private OutputCard outputCardIsr;
    private Bill billAllCurrency;

    @InjectMocks
    OutputCardServiceImpl inputCardService;
    @Mock
    InputCardRepository inputCardRepository;
    @Mock
    Utils utils;
    @Mock
    BillRepository billRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        outputCardUsa = OutputCard.builder()
            .billUuid(BILL_UUID)
            .billName("testBill")
            .userUuid(USER_UUID)
            .subcategoryName("testSubcategory")
            .categoryName("testCategory")
            .unit("sht")
            .sum(10.0)
            .note("this is test buying")
            .discount(10.0)
            .userUuid("2222")
            .currency(Currency.USA)
            .inputCardUuid("121212")
            .count(2.0)
            .categoryUuid("2121")
            .subcategoryUuid("2222")
            .deleted(false)
            .build();
        outputCardIsr = OutputCard.builder()
            .billUuid(BILL_UUID)
            .billName("testBill")
            .userUuid(USER_UUID)
            .subcategoryName("testSubcategory")
            .categoryName("testCategory")
            .unit("sht")
            .sum(10.0)
            .note("this is test buying")
            .discount(10.0)
            .userUuid("2222")
            .currency(Currency.ISR)
            .inputCardUuid("121212")
            .count(2.0)
            .categoryUuid("2121")
            .subcategoryUuid("2222")
            .deleted(false)
            .build();
        outputCardUkr = OutputCard.builder()
            .billUuid(BILL_UUID)
            .billName("testBill")
            .userUuid(USER_UUID)
            .subcategoryName("testSubcategory")
            .categoryName("testCategory")
            .unit("sht")
            .sum(10.0)
            .note("this is test buying")
            .discount(10.0)
            .userUuid("2222")
            .currency(Currency.UKR)
            .inputCardUuid("121212")
            .count(2.0)
            .categoryUuid("2121")
            .subcategoryUuid("2222")
            .deleted(false)
            .build();
        billAllCurrency = Bill.builder()
            .billName("bill")
            .billUuid(BILL_UUID)
            .userUuid(USER_UUID)
            .currencyName(Currency.ALL)
            .mainBill(false)
            .description("")
            .sumIsr(10.0)
            .sumUkr(30.0)
            .sumUsa(20.0)
            .deleted(false)
            .build();
    }

    @Test
    void createInputCardIsr() {
        Optional<Bill> restoredBill = Optional.of(billAllCurrency);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(),
            eq(false))).thenReturn(restoredBill);
        when(billRepository.save(any(Bill.class))).thenReturn(billAllCurrency);
        when(inputCardRepository.save(any(OutputCard.class))).thenReturn(outputCardIsr);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        OutputCardDto outputCardDto = modelMapper.map(outputCardIsr, OutputCardDto.class);
        OutputCardDto inputCard = inputCardService.createInputCard(outputCardDto);

        assertEquals(0.0, billAllCurrency.getSumIsr());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), outputCardDto.getUserUuid());
        assertNotNull(outputCardDto.getBillUuid());
        assertNotNull(outputCardDto.getUserUuid());
    }
    @Test
    void createInputCardUsa() {
        Optional<Bill> restoredBill = Optional.of(billAllCurrency);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(),
            eq(false))).thenReturn(restoredBill);
        when(billRepository.save(any(Bill.class))).thenReturn(billAllCurrency);
        when(inputCardRepository.save(any(OutputCard.class))).thenReturn(outputCardUsa);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        OutputCardDto outputCardDto = modelMapper.map(outputCardUsa, OutputCardDto.class);
        OutputCardDto inputCard = inputCardService.createInputCard(outputCardDto);

        assertEquals(10.0, billAllCurrency.getSumUsa());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), outputCardDto.getUserUuid());
        assertNotNull(outputCardDto.getBillUuid());
        assertNotNull(outputCardDto.getUserUuid());
    }

    @Test
    void createInputCardUkr() {
        Optional<Bill> restoredBill = Optional.of(billAllCurrency);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(),
            eq(false))).thenReturn(restoredBill);
        when(billRepository.save(any(Bill.class))).thenReturn(billAllCurrency);
        when(inputCardRepository.save(any(OutputCard.class))).thenReturn(outputCardUkr);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        OutputCardDto outputCardDto = modelMapper.map(outputCardUkr, OutputCardDto.class);
        OutputCardDto inputCard = inputCardService.createInputCard(outputCardDto);

        assertEquals(20.0, billAllCurrency.getSumUkr());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), outputCardDto.getUserUuid());
        assertNotNull(outputCardDto.getBillUuid());
        assertNotNull(outputCardDto.getUserUuid());
    }
}
