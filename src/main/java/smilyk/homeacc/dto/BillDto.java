package smilyk.homeacc.dto;

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

    @NotNull(message="User uuid cannot be null")
    String userUuid;

    @NotNull(message="Bill name cannot be null")
    String billName;

    String billUuid;

    String description;

    Double sumIsr;

    Double sumUkr;

    Double sumUsa;

    @NotNull(message="currency of bill cannot be null")
    Currency currencyName;

    Boolean mainBill;



}
