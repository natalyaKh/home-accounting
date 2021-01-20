package smilyk.homeacc.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransferResourcesResponseDto {
    List<BillDto> responseBillDtoList;
}
