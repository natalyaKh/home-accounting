package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import smilyk.homeacc.enums.CategoryType;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    @ApiModelProperty(notes = "The application-specific user ID", required = true)
    @NotNull(message = "User uuid cannot be null")
    String userUuid;

    @ApiModelProperty(notes = "The name of category", required = true)
    @NotNull(message = "Category name cannot be null")
    String categoryName;
    @ApiModelProperty(notes = "The application-specific category ID", required = true)
    String categoryUuid;

    @ApiModelProperty(notes = "Category description")
    String description;

    @ApiModelProperty(notes = "Category type(INPUT or OUTPUT)", required = true)
    @NotNull(message = "Category type cannot be null")
    CategoryType type;

}
