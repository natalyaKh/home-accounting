package smilyk.homeacc.dto;


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
    @NotNull(message="User name cannot be null")
    @Size(min=2, message= "User name must not be less than two characters")
    private String firstName;
    @NotNull(message="Last name cannot be null"                                                                                                                                                                                                                                                                                                                                                                                                                                                             )
    @Size(min=2, message= "Last name must not be less than two characters")
    private String lastName;
    @NotNull(message="Email cannot be null")
    @Email(message = "You should put valid e-mail")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min=2, message= "Password must not be less than two characters")
    private String password;
}
