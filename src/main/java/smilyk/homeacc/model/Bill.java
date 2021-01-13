package smilyk.homeacc.model;

import lombok.*;
import smilyk.homeacc.enums.Currency;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "bills")
public class Bill extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5625497905333499186L;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false, unique = true)
    String billName;

    @Column(nullable = false, unique = true)
    String billUuid;

    @Column()
    String description;

    //by default = 0
    @Column(nullable = false)
    Double startSum;

//    by default = ISR
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Currency currencyName;

//    by default = false
    @Column(nullable = false)
    boolean deleted;

    @Column(nullable = false )
    Boolean mainBill;
}
