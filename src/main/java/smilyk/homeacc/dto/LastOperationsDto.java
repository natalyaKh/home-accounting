package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.CategoryType;

import javax.validation.constraints.NotNull;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class LastOperationsDto {

    @ApiModelProperty(notes = "period of searching")
    Date date;

    @ApiModelProperty(notes = "The bills name")
    @NotNull(message = "Bill name cannot be null")
    String billName;

    @ApiModelProperty(notes = "The name of cards category")
    String category;
    @ApiModelProperty(notes = "The name of cards subcategory")
    String subcategory;
    @ApiModelProperty(notes = "The type of operation")
    CategoryType type;
    @ApiModelProperty(notes = "The description")
    String description;
    @ApiModelProperty(notes = "The sum of operation")
    Double sum;
    @ApiModelProperty(notes = "The application-specific operation ID")
    String operationUuid;
    @ApiModelProperty(notes = "The curency of operation")
    String currency;
}
