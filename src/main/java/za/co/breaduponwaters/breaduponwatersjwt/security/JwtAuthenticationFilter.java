package za.co.breaduponwaters.breaduponwatersjwt.security;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static za.co.breaduponwaters.breaduponwatersjwt.constants.SecurityConstants.*;
import za.co.breaduponwaters.breaduponwatersjwt.models.ApplicationUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ApplicationUser ApplicationUser = new ObjectMapper().readValue(
                    request.getInputStream(), ApplicationUser.class
            );
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    ApplicationUser.getUsername(), ApplicationUser.getPassword(), new ArrayList<>()
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        byte[] signingKey = JWT_SECRET.getBytes();
        Key key = Keys.hmacShaKeyFor(signingKey);
        Claims claims = Jwts.claims()
                .setSubject(((User) auth.getPrincipal()).getUsername());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(exp)
                .compact();
        
        response.addHeader("token", token);
    }
}
