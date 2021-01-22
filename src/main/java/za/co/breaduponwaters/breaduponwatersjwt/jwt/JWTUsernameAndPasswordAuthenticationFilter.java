package za.co.breaduponwaters.breaduponwatersjwt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import za.co.breaduponwaters.breaduponwatersjwt.models.auth.ApplicationUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;

import static za.co.breaduponwaters.breaduponwatersjwt.constants.SecurityConstants.EXPIRATION_TIME;
import static za.co.breaduponwaters.breaduponwatersjwt.constants.SecurityConstants.JWT_SECRET;

@AllArgsConstructor
public class JWTUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            ApplicationUser ApplicationUser = new ObjectMapper().readValue(
                    request.getInputStream(), ApplicationUser.class
            );
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    ApplicationUser.getUsername(), ApplicationUser.getPassword(), new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        byte[] signingKey = JWT_SECRET.getBytes();
        Key key = Keys.hmacShaKeyFor(signingKey);
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(signingKey))
                .compact();
        response.addHeader("Authorization", String.format("Bearer %s", token));
    }
}
