package za.co.breaduponwaters.breaduponwatersjwt.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.breaduponwaters.breaduponwatersjwt.models.auth.ApplicationUser;
import za.co.breaduponwaters.breaduponwatersjwt.repositories.auth.ApplicationUserRepository;

@RequestMapping("/users")
@RestController
@AllArgsConstructor
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/record")
    public void signup(@RequestBody ApplicationUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
    }


}
