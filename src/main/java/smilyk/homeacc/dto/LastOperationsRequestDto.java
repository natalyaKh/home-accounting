package smilyk.homeacc.dto;

import lombok.*;
import smilyk.homeacc.enums.Period;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LastOperationsRequestDto {
    Period period;
}
