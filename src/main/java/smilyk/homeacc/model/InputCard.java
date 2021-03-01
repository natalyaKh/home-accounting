package smilyk.homeacc.model;

import lombok.*;
import smilyk.homeacc.enums.Currency;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "input")
public class InputCard extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5152647687223062984L;

    @Column(nullable = false, unique = true)
     String inputCardUuid;

    @Column(nullable = false)
     String userUuid;

    @Column(nullable = false)
    String billName;

    @Column(nullable = false)
    String billUuid;

    @Column(nullable = false)
    String categoryName;

    @Column(nullable = false)
    String subcategoryName;

    @Column(nullable = false)
    String subcategoryUuid;

    @Column(nullable = false)
    String categoryUuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Currency currency;

    //by default = 0
    @Column(nullable = false)
    Double sum;

    //by default = 0
    @Column(nullable = false)
    Double count;

    //ед измерения
    @Column()
    String unit;

    @Column(nullable = false)
     Date createCardDate;

    @Column()
    String note;

    //    by default = false
    @Column(nullable = false)
    boolean deleted;
}
