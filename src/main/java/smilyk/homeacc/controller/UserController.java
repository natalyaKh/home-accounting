package smilyk.homeacc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import smilyk.homeacc.dto.OperationStatuDto;
import smilyk.homeacc.dto.UserDto;
import smilyk.homeacc.enums.RequestOperationName;
import smilyk.homeacc.enums.RequestOperationStatus;
import smilyk.homeacc.service.user.UserService;
import smilyk.homeacc.service.validation.ValidatorService;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;

@RestController
@Api( value = "home-accounting" , description = "Operations pertaining to users in HomeAccounting" )

@RequestMapping("v1/user")

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ValidatorService validatorService;

    /**
     * creating user
     *
     * @param userDto
     * @return userDto
     */

    @ApiOperation(value = "Create user", response = UserDto.class)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDto createUser(@Validated @RequestBody UserDto userDto) throws MessagingException {
        validatorService.checkUserUnique(userDto.getEmail());
        return userService.createUser(userDto);
    }

    /**
     * get user by his Uuid
     *
     * @param userUuid
     * @return userDto
     */
//checked
    @ApiOperation(value = "Search user by his uuid", response = UserDto.class)
    @GetMapping("/{userUuid}")
    public UserDto getUserByUuid(@PathVariable String userUuid) {
        return userService.getUserByUserUuid(userUuid);
    }

    /**
     * get all users
     *
     * @return List<userDto>
     */

    @ApiOperation(value = "View a list of users")
    @GetMapping()
    public List<UserDto> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getAllUsers(page, limit);
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        returnValue = new ModelMapper().map(users, listType);
        return returnValue;
    }

    /**
     * deleted user
     *
     * @param userUuid
     * @return SUCCESS or ERROR
     */

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/{userUuid}")
    public OperationStatuDto deleteUser(@PathVariable String userUuid) {
        OperationStatuDto returnValue = new OperationStatuDto();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(userUuid);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    /**
     * verification e-mail
     *
     * @param token
     * @return ERROR or SUCCESS
     * http://localhost:8082/user/email-verification?token=sdfsdf
     */

    @ApiOperation(value = "check if email confirm")
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

//    TODO create test
    /**
     * method that we need for validation registerUser from front
     * @param email
     * return True or False
     */

//    @GetMapping("/valid/{email}")
//    public Boolean getUserByUserEmail(@PathVariable String email){
//        return userService.getUserByEmailForValidation(email);
//    }

}
