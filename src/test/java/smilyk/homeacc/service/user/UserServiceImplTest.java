package smilyk.homeacc.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.mail.MailSenderImpl;
import smilyk.homeacc.utils.Utils;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    String userUuid = "1111";
    String encryptedPassword = "1234";
    String emailVerificationToken = "12345";
    String userLastName = "UserLastName";
    String userFirstName = "UserFirstName";
    String email = "mail@mail.com";
    String password = "1111";
    User user;
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;
    @Mock
    Utils utils;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    MailSenderImpl mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = User.builder()
                .firstName(userFirstName)
                .lastName(userLastName)
                .encryptedPassword(encryptedPassword)
                .deleted(true)
                .email(email)
                .userUuid(userUuid)
                .emailVerificationStatus(false)
                .emailVerificationToken(emailVerificationToken)
                .build();
    }

    @Test
    void testCreateUser() throws MessagingException {

//        userEntity.setUserUuid(utils.generateUserUuid().toString());
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
//        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
//        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserUuid()));
        when(utils.generateEmailVerificationToken(anyString())).thenReturn(emailVerificationToken);
//        userRepository.save(userEntity);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userDto = UserDto.builder()
                .email(email)
                .firstName(userFirstName)
                .lastName(userLastName)
                .userUuid(userUuid)
                .password(password)
                .build();
        UserDto storedUserDto = userService.createUser(userDto);

        assertNotNull(storedUserDto);
        assertEquals(user.getFirstName(), storedUserDto.getFirstName());

    }

    @Test
    void testGetUserByUserUuid() {

        Optional<User> returnCacheValue = Optional.of( user);
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(returnCacheValue);
        UserDto userDto = userService.getUserByUserUuid(userUuid);
        assertNotNull(userDto);
        assertEquals("UserFirstName", userDto.getFirstName());
    }

    @Test
    void testGetUserByUserUuidException() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> userService.getUserByUserUuid(userUuid));
    }
}