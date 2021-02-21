package smilyk.homeacc.dto;

import lombok.*;
import smilyk.homeacc.enums.CategoryType;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    @NotNull(message="User uuid cannot be null")
    String userUuid;

    @NotNull(message="Category name cannot be null")
    String categoryName;

    String description;

    @NotNull(message="Category type cannot be null")
    CategoryType type;

}
