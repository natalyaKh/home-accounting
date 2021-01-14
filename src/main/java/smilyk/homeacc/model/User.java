package smilyk.homeacc.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4309721365940075858L;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column()
    private String emailVerificationToken;

    @Column(nullable = false)
    private boolean deleted;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;


}
