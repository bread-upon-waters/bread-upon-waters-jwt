package za.co.breaduponwaters.breaduponwatersjwt.models.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@Data
public class ApplicationUser implements UserDetails, Serializable {

    @Id  @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", updatable=false, nullable=false)
    private Long id;
    private final GrantedAuthority[] authorities;
    private final String username;
    private String password;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    @Transient
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>(0);
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
        return list;
    }
}
