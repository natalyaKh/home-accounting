package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.Period;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LastOperationsRequestDto {
    @ApiModelProperty(notes = "Period od operation", required = true)
        @NotNull
    Period period;
}
