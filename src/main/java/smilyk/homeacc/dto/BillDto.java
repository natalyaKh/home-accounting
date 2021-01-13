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

    @NotNull(message="Bill uuid cannot be null")
    String billUuid;

    @NotNull(message="Description cannot be null")
    String description;

    @NotNull(message="Sum in israel shekel cannot be null")
    Double sumIsr;


    @NotNull(message="Sum in ukrainian hryvna cannot be null")
    Double sumUkr;

    @NotNull(message="Sum in american dollar cannot be null")
    Double sumUsa;

    @NotNull(message="currency of bill cannot be null")
    Currency currencyName;

    @NotNull(message="mainBill cannot be null")
    Boolean mainBill;



}
