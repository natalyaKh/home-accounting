package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferResourcesBetweenBillsDto {
    @ApiModelProperty(notes = "The name of FROM bill", required = true)
    String billNameFrom;
    @ApiModelProperty(notes = "The  name of TO bill", required = true)
    String billNameTo;

    @ApiModelProperty(notes = "The application-specific user ID", required = true)
    String userUuid;

    @ApiModelProperty(notes = "The sum of ransfer")
    Double sum;

    @ApiModelProperty(notes = "The currency of transfer")
    Currency currency;
}
