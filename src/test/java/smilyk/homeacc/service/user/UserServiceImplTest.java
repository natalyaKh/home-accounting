package smilyk.homeacc.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
    }

    @Test
    void testGetUserByUserUuid() {
        User user = User.builder()
                .firstName("UserFirstName")
                .lastName("UserLastName")
                .encryptedPassword("1234")
                .deleted(true)
                .email("user@mail.com")
                .userUuid("1111")
                .emailVerificationStatus(false)
                .emailVerificationToken("12345")
                .build();
        Optional<User> returnCacheValue = Optional.of((User) user);
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(returnCacheValue);
        UserDto userDto = userService.getUserByUserUuid("1111");
        assertNotNull(userDto);
        assertEquals("UserFirstName", userDto.getFirstName());
    }

    @Test
    void testGetUserByUserUuidException() {
        when(userRepository.findByUserUuidAndDeleted(anyString(), eq(false)))
                .thenReturn(Optional.empty());
        assertThrows(HomeaccException.class, () -> userService.getUserByUserUuid("1111"));
    }
}