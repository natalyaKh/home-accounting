package smilyk.homeacc.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestModel {

    String email;
    String encryptedPassword;
}
