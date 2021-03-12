package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationMailDto {

    String tokenValue;
    String userName;
    String userLastName;
    String email;
}
