package smilyk.homeacc.service.inputCard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import smilyk.homeacc.dto.InputCardDto;
import smilyk.homeacc.enums.Currency;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.InputCard;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.InputCardRepository;
import smilyk.homeacc.utils.Utils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class InputCardServiceImplTest {

    private static final String BILL_UUID = "1111";
    private static final String USER_UUID = "3333";
    ModelMapper modelMapper = new ModelMapper();
    private InputCard inputCardUsa;
    private InputCard inputCardUkr;
    private InputCard inputCardIsr;
    private Bill billAllCurrency;

    @InjectMocks
    InputCardServiceImpl inputCardService;
    @Mock
    InputCardRepository inputCardRepository;
    @Mock
    Utils utils;
    @Mock
    BillRepository billRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        inputCardUsa = InputCard.builder()
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
        inputCardIsr = InputCard.builder()
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
        inputCardUkr = InputCard.builder()
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
        when(inputCardRepository.save(any(InputCard.class))).thenReturn(inputCardIsr);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        InputCardDto inputCardDto = modelMapper.map(inputCardIsr, InputCardDto.class);
        InputCardDto inputCard = inputCardService.createInputCard(inputCardDto);

        assertEquals(0.0, billAllCurrency.getSumIsr());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), inputCardDto.getUserUuid());
        assertNotNull(inputCardDto.getBillUuid());
        assertNotNull(inputCardDto.getUserUuid());
    }
    @Test
    void createInputCardUsa() {
        Optional<Bill> restoredBill = Optional.of(billAllCurrency);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(),
            eq(false))).thenReturn(restoredBill);
        when(billRepository.save(any(Bill.class))).thenReturn(billAllCurrency);
        when(inputCardRepository.save(any(InputCard.class))).thenReturn(inputCardUsa);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        InputCardDto inputCardDto = modelMapper.map(inputCardUsa, InputCardDto.class);
        InputCardDto inputCard = inputCardService.createInputCard(inputCardDto);

        assertEquals(10.0, billAllCurrency.getSumUsa());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), inputCardDto.getUserUuid());
        assertNotNull(inputCardDto.getBillUuid());
        assertNotNull(inputCardDto.getUserUuid());
    }

    @Test
    void createInputCardUkr() {
        Optional<Bill> restoredBill = Optional.of(billAllCurrency);
        when(billRepository.findByBillNameAndUserUuidAndDeleted(anyString(), anyString(),
            eq(false))).thenReturn(restoredBill);
        when(billRepository.save(any(Bill.class))).thenReturn(billAllCurrency);
        when(inputCardRepository.save(any(InputCard.class))).thenReturn(inputCardUkr);
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
        InputCardDto inputCardDto = modelMapper.map(inputCardUkr, InputCardDto.class);
        InputCardDto inputCard = inputCardService.createInputCard(inputCardDto);

        assertEquals(20.0, billAllCurrency.getSumUkr());
        assertNotNull(inputCard);
        assertEquals(inputCard.getUserUuid(), inputCardDto.getUserUuid());
        assertNotNull(inputCardDto.getBillUuid());
        assertNotNull(inputCardDto.getUserUuid());
    }
}
