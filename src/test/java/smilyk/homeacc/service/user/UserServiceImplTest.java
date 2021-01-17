package smilyk.homeacc.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.dto.VerificationMailDto;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.mail.MailSenderImpl;
import smilyk.homeacc.utils.Utils;

import javax.mail.MessagingException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    ModelMapper modelMapper = new ModelMapper();
    String USER_UUID = "1111";
    String ENCRYPTED_PASSWORD = "1234";
    String EMAIL_VERIFICATION_TOKEN = "12345";
    String USER_LAST_NAME = "UserLastName";
    String USER_FIRST_NAME = "UserFirstName";
    String EMAIL = "mail@mail.com";
    User user;
    User userNotDeleted;
    User userDeleted ;
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
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .deleted(false)
                .email(EMAIL)
                .userUuid(USER_UUID)
                .emailVerificationStatus(false)
                .emailVerificationToken(EMAIL_VERIFICATION_TOKEN)
                .build();
        userNotDeleted = user = User.builder()
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .deleted(false)
                .email(EMAIL)
                .userUuid(USER_UUID)
                .emailVerificationStatus(false)
                .emailVerificationToken(EMAIL_VERIFICATION_TOKEN)
                .build();
        userDeleted = user = User.builder()
                .firstName(USER_FIRST_NAME)
                .lastName(USER_LAST_NAME)
                .encryptedPassword(ENCRYPTED_PASSWORD)
                .deleted(true)
                .email(EMAIL)
                .userUuid(USER_UUID)
                .emailVerificationStatus(false)
                .emailVerificationToken(EMAIL_VERIFICATION_TOKEN)
                .build();
    }

    @Test
    void testCreateUser() throws MessagingException {

//        userEntity.setUserUuid(utils.generateUserUuid().toString());
        when(utils.generateUserUuid()).thenReturn(UUID.randomUUID());
//        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
//        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserUuid()));
        when(utils.generateEmailVerificationToken(anyString())).thenReturn(EMAIL_VERIFICATION_TOKEN);
//        userRepository.save(userEntity);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto storedUserDto = userService.createUser(userDto);

        assertNotNull(storedUserDto);
        assertEquals(user.getFirstName(), storedUserDto.getFirstName());
        assertNotNull(storedUserDto.getUserUuid());
        userDto.setPassword("");
        assertEquals(userDto, storedUserDto);
//        checking how times called method
        verify(utils, times(1)).generateUserUuid();
        verify(bCryptPasswordEncoder, times(1)).encode(ENCRYPTED_PASSWORD);
        verify(userRepository, times(1)).save(any(User.class));
//        instruct method from testing
        Mockito.doNothing().when(mailService).sendVerificationEmailMail(any(VerificationMailDto.class));

    }

    @Test
    void testGetUserByUserUuid() {

        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(returnCacheValue);
        UserDto userDto = userService.getUserByUserUuid(USER_UUID);
        assertNotNull(userDto);
        assertEquals("UserFirstName", userDto.getFirstName());
    }

    @Test
    void testGetUserByUserUuidException() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> userService.getUserByUserUuid(USER_UUID));
    }

    @Test
    void getAllUsersLimitOneUser() {
        List<User> usersList = new ArrayList<>();
        usersList.add(user);
        usersList.add(userDeleted);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> userPage = new PageImpl<>(usersList, pageable, usersList.size());
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        List<User> users = Arrays.asList(user);
        List<UserDto> storedUsers = userService.getAllUsers(0, 1);

        assertEquals(users.size(), storedUsers.size());
        assertEquals(users.get(0).getFirstName(), storedUsers.get(0).getFirstName());
    }

    @Test
    void getAllUsersLimitTenUser() {

        List<User> usersList = Arrays.asList(user, userNotDeleted);
        Pageable pageable = PageRequest.of(0, 1);
        Page<User> userPage = new PageImpl<>(usersList, pageable, usersList.size());
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        List<UserDto> storedUsers = userService.getAllUsers(0, 1);
        assertEquals(usersList.size(), storedUsers.size());
        assertEquals(usersList.get(0).getFirstName(), storedUsers.get(0).getFirstName());
    }

    @Test
    void getAllUsersLimitTenUserAnDeletedUser() {

        List<User> usersList = Arrays.asList(user, userDeleted);

        Pageable pageable = PageRequest.of(0, 1);
        Page<User> userPage = new PageImpl<>(usersList, pageable, usersList.size());
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        List<User> users = new ArrayList<>();
        users.add(user);
        List<UserDto> storedUsers = userService.getAllUsers(0, 1);
        assertEquals(users.size(), storedUsers.size());
        assertEquals(users.get(0).getFirstName(), storedUsers.get(0).getFirstName());

    }

    @Test
    void deleteUser() {
        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findUserByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(returnCacheValue);
        when(userRepository.save(user)).thenReturn(user);
        userService.deleteUser(USER_UUID);

        assertTrue(user.isDeleted());
    }

    @Test
    void DeleteUserWithException(){
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> userService.getUserByUserUuid(USER_UUID));
    }


}