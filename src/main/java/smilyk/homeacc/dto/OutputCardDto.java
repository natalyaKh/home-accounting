package smilyk.homeacc.dto;

import lombok.*;
import smilyk.homeacc.enums.Currency;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OutputCardDto {
    @NotNull(message="User uuid cannot be null")
    String userUuid;
    @NotNull(message="Bill name cannot be null")
    String billName;
    @NotNull(message="Bill uuid cannot be null")
    String billUuid;
    @NotNull(message="Subategory name cannot be null")
    String subCategoryName;
    String subCategoryUuid;
    @NotNull(message="Category name cannot be null")
    String categoryName;
    String categoryUuid;
    String note;
    @NotNull(message="Currency cannot be null")
    Currency currency;
    @NotNull(message="Sum cannot be null")
    Double sum;
    Double discount;
    @NotNull(message="Count cannot be null")
    Double count;
    String unit;
    @NotNull(message = "Create date can not bu null")
    Date createCardDate;

}
