package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.Currency;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDto {
    @ApiModelProperty(notes = "The application-specific user ID", required = true)
    @NotNull(message="User uuid cannot be null")
    String userUuid;

    @ApiModelProperty(notes = "The bills name", required = true)
    @NotNull(message="Bill name cannot be null")
    String billName;

    @ApiModelProperty(notes = "The application-specific bill ID", required = true)
    String billUuid;

    @ApiModelProperty(notes = "The bills description")
    String description;

    @ApiModelProperty(notes = "The sum in israel shekel")
    Double sumIsr;

    @ApiModelProperty(notes = "The sum in ukrainian hryvna")
    Double sumUkr;

    @ApiModelProperty(notes = "The sum in american dollar")
    Double sumUsa;

    @ApiModelProperty(notes = "The name of bills currency (can be ALL, UKR, ISR, USA)")
    @NotNull(message="currency of bill cannot be null")
    Currency currencyName;

    @ApiModelProperty(notes = "Is the bill main (van be only one mein bill by user)", required = true)
    Boolean mainBill;



}
