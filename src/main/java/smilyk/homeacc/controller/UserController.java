package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.OperationStatuDto;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.enums.RequestOperationName;
import smilyk.homeacc.enums.RequestOperationStatus;
import smilyk.homeacc.service.user.UserService;
import smilyk.homeacc.service.validation.ValidatorService;

import javax.mail.MessagingException;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ValidatorService validatorService;

    @PostMapping()
    public UserDto createUser(@Validated @RequestBody UserDto userDto) throws MessagingException {
        validatorService.checkUserUnique(userDto.getEmail());
        return userService.createUser(userDto);
    }

    /**
     * http://localhost:8082/user/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification")
    public OperationStatuDto verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatuDto returnValue = new OperationStatuDto();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        boolean isVerified = userService.verifyEmailToken(token);
        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        return returnValue;
    }


}
