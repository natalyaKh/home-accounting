package smilyk.homeacc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smilyk.homeacc.dto.OperationStatuDto;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.enums.RequestOperationName;
import smilyk.homeacc.enums.RequestOperationStatus;
import smilyk.homeacc.service.user.UserServiceImpl;
import smilyk.homeacc.service.validation.ValidatorService;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private static final String USER_UUID = "1111";
    private static final String USER_FIRST_NAME = "UsersFirstName";
    private static final String USER_SECOND_NAME = "UsersSecondName";
    private static final String USER_EMAIL = "mail@mail.com";
    UserDto userDto;
    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    @Mock
    ValidatorService validatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDto = UserDto.builder()
                .userUuid(USER_UUID)
                .firstName(USER_FIRST_NAME)
                .lastName(USER_SECOND_NAME)
                .email(USER_EMAIL)
                .password("")
                .build();
    }

    @Test
    void createUser() throws MessagingException {
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        UserDto restoredUserDto = userController.createUser(userDto);

        assertNotNull(restoredUserDto);
        assertEquals(userDto, restoredUserDto);
    }

    @Test
    void getUserByUuid() {
        when(userService.getUserByUserUuid(anyString())).thenReturn(userDto);
        UserDto restoredUserDto = userController.getUserByUuid("1111");

        assertNotNull(restoredUserDto);
        assertEquals(userDto, restoredUserDto);
    }

    @Test
    void getUsers() {
        List<UserDto> userDtoList = Arrays.asList(userDto);
        when(userService.getAllUsers(0, 3)).thenReturn(userDtoList);
        List<UserDto> restoredUserDtoList = userController.getUsers(0, 3);

        assertNotNull(restoredUserDtoList);
        assertEquals(userDtoList, restoredUserDtoList);
    }

    @Test
    void deleteUser() {
       userService.deleteUser(USER_UUID);

       OperationStatuDto deleted = userController.deleteUser(USER_UUID);

       assertEquals(RequestOperationName.DELETE.name(), deleted.getOperationName());
       assertEquals(RequestOperationStatus.SUCCESS.name(), deleted.getOperationResult());


    }


}