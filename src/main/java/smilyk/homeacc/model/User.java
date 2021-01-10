package smilyk.homeacc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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

    private String emailVerificationToken;

    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;


    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userId) {
        this.userUuid = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    public User() {
    }

    public User(String userUuid, String firstName, String lastName, String email, String encryptedPassword,
                String emailVerificationToken, Boolean emailVerificationStatus) {
        this.userUuid = userUuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.emailVerificationToken = emailVerificationToken;
        this.emailVerificationStatus = emailVerificationStatus;
    }
}
