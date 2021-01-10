package smilyk.homeacc.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ErrorEmailDto {
    String userEmail;
    String userName;
}
