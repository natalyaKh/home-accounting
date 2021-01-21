package smilyk.homeacc.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import smilyk.homeacc.dto.UserDto;

import javax.mail.MessagingException;
import java.util.List;



public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto) throws MessagingException;
    boolean verifyEmailToken(String token);
    UserDto getUserByUserUuid(String userUuid);
    List<UserDto> getAllUsers(int page, int limit);
    void deleteUser(String userUuid);

    UserDto getUserByEmail(String userName);
}
