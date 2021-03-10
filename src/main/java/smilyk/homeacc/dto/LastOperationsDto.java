package smilyk.homeacc.dto;

import lombok.*;
import smilyk.homeacc.enums.CategoryType;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LastOperationsDto {
    Date date;
    String billName;
    String category;
    String subcategory;
    CategoryType type;
    String description;
    Double sum;
    String operationUuid;
    String currency;
}
