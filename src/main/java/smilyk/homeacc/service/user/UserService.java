package smilyk.homeacc.service.user;

import org.springframework.stereotype.Service;
import smilyk.homeacc.dto.UserDto;

import javax.mail.MessagingException;

@Service


public interface UserService {
    UserDto createUser(UserDto userDto) throws MessagingException;

}
