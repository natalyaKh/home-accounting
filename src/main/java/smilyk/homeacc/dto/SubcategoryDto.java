package smilyk.homeacc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubcategoryDto {
    @ApiModelProperty(notes = "The application-specific user ID", required = true)
    @NotNull(message="User uuid cannot be null")
    String userUuid;

    @ApiModelProperty(notes = "name of subcategory", required = true)
    @NotNull(message="Subategory name cannot be null")
    String subcategoryName;
    @ApiModelProperty(notes = "The description", required = true)
    String description;
    @ApiModelProperty(notes = "The application-specific subcategory ID")
    String subcategoryUuid;


}
