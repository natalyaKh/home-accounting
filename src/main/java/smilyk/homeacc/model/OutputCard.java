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
@Table(name = "output")
public class OutputCard extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6834942411559736697L;

    @Column(nullable = false, unique = true)
    private String inputCardUuid;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false)
    String billName;

    @Column(nullable = false)
    String billUuid;

    @Column(nullable = false)
    String subcategoryUuid;

    @Column(nullable = false)
    String categoryUuid;
//    примечания
    @Column()
    String note;
    //    by default = false
    @Column(nullable = false)
    boolean deleted;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Currency currency;

    //by default = 0
    @Column(nullable = false)
    Double sum;
///скидка
    //by default = 0
    @Column(nullable = false)
    Double discount;

    //by default = 0
    @Column(nullable = false)
    Double count;
//ед измерения
    @Column()
    String unit;
    @Column(nullable = false)
    String categoryName;
    @Column(nullable = false)
    String subcategoryName;

}
