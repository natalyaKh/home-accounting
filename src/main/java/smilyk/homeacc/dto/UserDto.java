package smilyk.homeacc.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    @ApiModelProperty(notes = "The application-specific user ID. Min - 2 symbol", required = true)
    String userUuid;

    @ApiModelProperty(notes = "The name of user. Min - two characters ", required = true)
    @NotNull(message="User name cannot be null")
    @Size(min=2, message= "User name must not be less than two characters")
    private String firstName;
    @ApiModelProperty(notes = "The surname of user. Min -two characters", required = true)
    @NotNull(message="Last name cannot be null"                                                                                                                                                                                                                                                                                                                                                                                                                                                             )
    @Size(min=2, message= "Last name must not be less than two characters")
    private String lastName;

    @ApiModelProperty(notes = "The email of user", required = true)
    @NotNull(message="Email cannot be null")
    @Email(message = "You should put valid e-mail")
    private String email;
    @ApiModelProperty(notes = "The password of user. Min - two characters", required = true)
    @NotNull(message = "Password cannot be null")
    @Size(min=2, message= "Password must not be less than two characters")
    private String password;
}
