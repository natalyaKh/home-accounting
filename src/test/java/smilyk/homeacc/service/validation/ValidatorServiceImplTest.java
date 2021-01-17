package smilyk.homeacc.service.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smilyk.homeacc.constants.ValidatorConstants;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.Bill;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.BillRepository;
import smilyk.homeacc.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ValidatorServiceImplTest {

    String USER_UUID = "1111";
    String ENCRYPTED_PASSWORD = "1234";
    String EMAIL_VERIFICATION_TOKEN = "12345";
    String USER_LAST_NAME = "UserLastName";
    String USER_FIRST_NAME = "UserFirstName";
    String EMAIL = "mail@mail.com";
    User user;

    @InjectMocks
    ValidatorServiceImpl validatorService;
    @Mock
    UserRepository userRepository;

    @Mock
    BillRepository billRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .deleted(false)
                .email(EMAIL)
                .userUuid(USER_UUID)
                .emailVerificationStatus(false)
                .emailVerificationToken(EMAIL_VERIFICATION_TOKEN)
                .build();
    }

    @Test
    void testCheckUserUniqueNotValid() {
//        Optional<User> user = userRepository.findByEmailAndDeleted(email, false);
        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findByEmailAndDeleted(anyString(), eq(false)))
                .thenReturn(returnCacheValue);
        assertThrows(HomeaccException.class, () -> validatorService.checkUserUnique(USER_UUID));
    }

    @Test
    void testCheckUserUniqueValid() {
//        Optional<User> user = userRepository.findByEmailAndDeleted(email, false);
        when(userRepository.findByEmailAndDeleted(anyString(), eq(false)))
                .thenReturn(Optional.empty());
        validatorService.checkUserUnique("m@mail.com");

    }

    @Test
    void testCheckUserExistsNotValid() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(Optional.empty());

        assertThrows(HomeaccException.class, () -> validatorService.checkUserExists(USER_UUID));
    }

    @Test
    void testCheckUserExistsValid() {
        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false))).thenReturn(returnCacheValue);

        validatorService.checkUserExists(USER_UUID);
    }
//      LOGGER.info(ValidatorConstants.CHECK_BILLS_FOR_DELETED);
//    //        dont check bill per user - checked it before
//    Optional<Bill> billOptional = billRepository.findByBillNameAndDeleted(billName, false);
//        if(billOptional.get().getMainBill()){
//        LOGGER.error(ValidatorConstants.MAIN_BILL + billName + ValidatorConstants.CHANGE_MAIN_BILL);
//        throw new HomeaccException(ValidatorConstants.MAIN_BILL + billName + ValidatorConstants.CHANGE_MAIN_BILL);
//    }
    @Test
//    TODO
    void testCheckMainBillsForDeletedNotValid() {

    }

    @Test
//    TODO
    void testCheckMainBillsForDeletedValid() {

    }

    @Test
//    TODO
    void checkUniqueBill() {
    }

    @Test
//    TODO
    void ckeckBill() {
    }

    @Test
        //    TODO
    void checkBillByUser() {
    }

    @Test
        //    TODO
    void checkBillByUserAndCurrency() {
    }

    @Test
        //    TODO
    void checkCurrencyNameValid() {
    }

    @Test
        //    TODO
    void checkMainBill() {
    }
}