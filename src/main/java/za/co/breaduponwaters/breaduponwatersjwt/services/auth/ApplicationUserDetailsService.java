package za.co.breaduponwaters.breaduponwatersjwt.services.auth;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import za.co.breaduponwaters.breaduponwatersjwt.repositories.auth.ApplicationUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return applicationUserRepository.findByUsername(username)
                .map((applicationUser) -> {
                    //List<GrantedAuthority> grantedAuthorities = applicationUser.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
                    return new User(applicationUser.getUsername(), applicationUser.getPassword(), Collections.<GrantedAuthority>emptyList());
                })
                .orElseThrow(()-> new UsernameNotFoundException(String.format("Username %s not found", username)));

    }
}
