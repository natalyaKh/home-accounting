package smilyk.homeacc.dto;

import lombok.*;
import smilyk.homeacc.enums.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferResourcesBetweenBillsDto {
    String billNameFrom;
    String billNameTo;
    String userUuid;
    Double sum;
    Currency currency;
}
