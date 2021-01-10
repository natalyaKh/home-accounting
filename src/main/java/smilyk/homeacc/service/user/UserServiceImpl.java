package smilyk.homeacc.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.UserConstants;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.dto.VerificationMailDto;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.mail.MailService;
import smilyk.homeacc.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;

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
    @Autowired
    MailService mailService;

    @Override
    public UserDto createUser(UserDto userDto) throws MessagingException {
        User userEntity = userDtoToUserEntity(userDto);
        userEntity.setUserUuid(utils.generateUserUuid().toString());
//        password
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
//        verificationEmail
        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserUuid()));
//        send e-mail to user
        LOGGER.info(UserConstants.SEND_VERIFICATION_EMAIL + userEntity.getEmail());
        VerificationMailDto verificationMail = VerificationMailDto.builder()
                .email(userEntity.getEmail())
                .tokenValue(userEntity.getEmailVerificationToken())
                .userName(userEntity.getFirstName())
                .userLastName(userEntity.getLastName())
                .build();
        mailService.sendVerificationEmailMail(verificationMail);
//        saving-user
        userRepository.save(userEntity);
        userDto.setPassword("");
        return userDto;
    }

    private User userDtoToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
