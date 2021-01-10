package smilyk.homeacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.service.user.UserService;
import smilyk.homeacc.service.validation.ValidatorService;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ValidatorService validatorService;

    @PostMapping()
    public UserDto createUser(@Validated @RequestBody UserDto userDto) {
        validatorService.checkUserUnique(userDto.getEmail());
        return userService.createUser(userDto);
    }


}
