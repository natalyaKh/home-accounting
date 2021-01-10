package smilyk.homeacc.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smilyk.homeacc.constants.ValidatorConstants;
import smilyk.homeacc.exceptions.HomeaccException;
import smilyk.homeacc.model.User;
import smilyk.homeacc.repo.UserRepository;
import smilyk.homeacc.service.user.UserServiceImpl;

import java.util.Optional;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public void checkUserUnique(String email) {
        LOGGER.info(ValidatorConstants.CHECK_USER_WITH_EMAIL + email);
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isEmpty()) {
            LOGGER.error(ValidatorConstants.NOT_UNIQUE_USER + email);
            throw new HomeaccException(ValidatorConstants.NOT_UNIQUE_USER + email);
        }
    }
}
