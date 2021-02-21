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
@Table(name = "subcategory")
public class Subcategory extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 6834942411559736697L;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false)
    String subcategoryName;

    @Column(nullable = false, unique = true)
    String subcategoryUuid;

    @Column()
    String description;

    //    by default = false
    @Column(nullable = false)
    boolean deleted;


}
