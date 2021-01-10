package smilyk.homeacc.service.user;

import org.springframework.stereotype.Service;
import smilyk.homeacc.dto.UserDto;

@Service


public interface UserService {
    UserDto createUser(UserDto userDto);

}
