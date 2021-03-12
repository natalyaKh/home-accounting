package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.Currency;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InputCardDto {
    String inputCardUuid;

    @ApiModelProperty(notes = "The application-specific user ID", required = true)
    @NotNull(message = "User uuid cannot be null")
    String userUuid;

    @ApiModelProperty(notes = "The bills name", required = true)
    @NotNull(message = "Bill name cannot be null")
    String billName;

    @ApiModelProperty(notes = "The application-specific bill ID", required = true)
    @NotNull(message = "Bill uuid cannot be null")
    String billUuid;

    @ApiModelProperty(notes = "name of category", required = true)
    @NotNull(message = "Category name cannot be null")
    String categoryName;

    @ApiModelProperty(notes = "name of category,", required = true)
    @NotNull(message = "Subategory name cannot be null")
    String subcategoryName;
    @ApiModelProperty(notes = "The application-specific subcategory ID", required = true)
    String subcategoryUuid;

    @ApiModelProperty(notes = "The application-specific category ID")
    String categoryUuid;

    @ApiModelProperty(notes = "The currency of category", required = true)
    @NotNull(message = "Currency cannot be null")
    Currency currency;

    @ApiModelProperty(notes = "The price of product", required = true)
    @NotNull(message = "Sum cannot be null")
    Double sum;

    @ApiModelProperty(notes = "The count of product", required = true)
    @NotNull(message = "Count cannot be null")
    Double count;

    @ApiModelProperty(notes = "The units of product")
    String unit;

    @ApiModelProperty(notes = "The date when input card created", required = true)
    @NotNull(message = "Create date can not bu null")
    Date createCardDate;

    @ApiModelProperty(notes = "The description")
    String note;

}
