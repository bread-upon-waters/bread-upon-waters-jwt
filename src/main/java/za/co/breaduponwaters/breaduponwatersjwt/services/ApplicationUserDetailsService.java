package za.co.breaduponwaters.breaduponwatersjwt.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.breaduponwaters.breaduponwatersjwt.models.ApplicationUser;
import za.co.breaduponwaters.breaduponwatersjwt.repositories.ApplicationUserRepository;

import java.util.Collections;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.<GrantedAuthority>emptyList());
    }
}
