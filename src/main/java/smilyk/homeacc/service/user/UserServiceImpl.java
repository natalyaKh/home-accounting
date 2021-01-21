package smilyk.homeacc.service.user;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smilyk.homeacc.constants.UserConstants;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.dto.VerificationMailDto;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.mail.MailService;
import smilyk.homeacc.utils.Utils;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;

    @Override
//    creating test
    public UserDto createUser(UserDto userDto) throws MessagingException {
        User userEntity = userDtoToUserEntity(userDto);
        userEntity.setUserUuid(utils.generateUserUuid().toString());
//        userEntity.setUserUuid(Utils.generateUserUuid().toString());
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
        LOGGER.info(UserConstants.USER_WITH_UUID + userEntity.getUserUuid() + UserConstants.SAVED);
        userDto.setPassword("");
        return userDto;
    }

    @Override
    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;
        // Find user by token
        Optional<User> userOptional = userRepository.findUserByEmailVerificationToken(token);
        //if token exists (dont null), user didn`t make verification before and he should do it
        if (userOptional.isPresent()) {
            boolean hastokenExpired = utils.hasTokenExpired(token);
            User userEntity = userOptional.get();
            if (!hastokenExpired) {
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                LOGGER.info(UserConstants.VERIFICATION_EMAIL + userEntity.getEmail());
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    //    creating test
    public UserDto getUserByUserUuid(String userUuid) {
        Optional<User> userOptional = userRepository.findByUserUuidAndDeleted(userUuid, false);
        if (!userOptional.isPresent()) {
            LOGGER.info(UserConstants.USER_WITH_UUID + userUuid + UserConstants.NOT_FOUND);
            throw new HomeaccException(UserConstants.USER_WITH_UUID + userUuid + UserConstants.NOT_FOUND);
        }
        User userEntity = userOptional.get();
        UserDto userDto = userEntityToUserDto(userEntity);
        userDto.setPassword("");
        LOGGER.info(UserConstants.USER_WITH_UUID + userEntity.getUserUuid() + UserConstants.FOUND);
        return userDto;
    }

    @Override
//    creating test
    public List<UserDto> getAllUsers(int page, int limit) {
        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> usersPage = userRepository.findAll(pageableRequest);
        List<User> users = usersPage.getContent();

        List<UserDto> returnValue = new ArrayList<>();
        users.stream().filter(u -> !u.isDeleted()).map(this::userEntityToUserDto).forEachOrdered(returnValue::add);
        LOGGER.info(UserConstants.USERS_LIST);
        return returnValue;
    }

    @Transactional
    @Override
    public void deleteUser(String userUuid) {
        Optional<User> userOptional = userRepository.findUserByUserUuidAndDeleted(userUuid, false);
        if (!userOptional.isPresent()) {
            LOGGER.info(UserConstants.USER_WITH_UUID + userUuid + UserConstants.NOT_FOUND);
            throw new HomeaccException(UserConstants.USER_WITH_UUID + userUuid + UserConstants.NOT_FOUND);
        }
        User userEntity = userOptional.get();
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
        LOGGER.info(UserConstants.USER_WITH_UUID + userUuid + UserConstants.DELETED);
    }

    private UserDto userEntityToUserDto(User userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }


    private User userDtoToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
