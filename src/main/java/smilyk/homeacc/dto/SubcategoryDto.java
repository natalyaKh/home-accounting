package smilyk.homeacc.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubcategoryDto {
    @NotNull(message="User uuid cannot be null")
    String userUuid;
    @NotNull(message="Subategory name cannot be null")
    String subCategoryName;
    @NotNull(message="Category name cannot be null")
    String description;


}
