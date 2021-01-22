package za.co.breaduponwaters.breaduponwatersjwt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import za.co.breaduponwaters.breaduponwatersjwt.constants.SecurityConstants;
import za.co.breaduponwaters.breaduponwatersjwt.models.auth.ApplicationUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerificationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.replace("Bearer ", "");
        try{
            Jwt<Header, Claims> claimsJwt = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes()))
                    .build()
                    .parseClaimsJwt(token);

            Claims body = claimsJwt.getBody();
            String username = body.getSubject();
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (JwtException e){
            throw new IllegalStateException(String.format("Token %s cannot be trusted!", token));
        }
        filterChain.doFilter(request, response);
    }
}
