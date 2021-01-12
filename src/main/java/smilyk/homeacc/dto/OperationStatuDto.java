package smilyk.homeacc.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationStatuDto {
    private String operationResult;
    private String operationName;
}

