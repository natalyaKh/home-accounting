package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorEmailDto {
    @ApiModelProperty(notes = "The email of user")
    String userEmail;
    @ApiModelProperty(notes = "The userName of user")
    String userName;
}
