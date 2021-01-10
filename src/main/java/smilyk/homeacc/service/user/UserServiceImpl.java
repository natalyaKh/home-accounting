package smilyk.homeacc.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
    ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User userEntity = userDtoToUserEntity(userDto);
        userEntity.setUserUuid(utils.generateUserUuid().toString());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(userEntity);
        userDto.setPassword("");
        return userDto;
    }

    private User userDtoToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
