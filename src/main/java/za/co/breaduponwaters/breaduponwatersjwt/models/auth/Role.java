package za.co.breaduponwaters.breaduponwatersjwt.models.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="ROLE")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ROLE_ID")
    private long id;
    @ManyToOne
    @JoinColumn(name="username")
    private ApplicationUser user;
    private String role;
}
