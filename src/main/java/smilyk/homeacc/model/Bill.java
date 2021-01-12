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
@Table(name = "bills")
public class Bill extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 5625497905333499186L;

    @Column(nullable = false)
    private String userUuid;

    @Column(nullable = false)
    String billName;

    @Column(nullable = false)
    String billUuid;

    @Column()
    String description;

    @Column(nullable = false)
    String startSum;

    @Column(nullable = false)
    boolean deleted;
}
