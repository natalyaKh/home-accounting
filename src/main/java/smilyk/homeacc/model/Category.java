package smilyk.homeacc.model;

import lombok.*;
import smilyk.homeacc.enums.CategoryType;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "category")
public class Category extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 7339291247852108694L;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false)
    String categoryName;

    @Column(nullable = false, unique = true)
    String categoryUuid;

    @Column()
    String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    CategoryType type;

    //    by default = false
    @Column(nullable = false)
    boolean deleted;

}
