package pl.agasior.interviewprep.configuration.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.agasior.interviewprep.dto.exceptions.AuthenticationException;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {
    private final Key tokenSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${security.jwt.expirationTime}")
    private Integer expirationTime;

    public String createToken(Authentication authentication) {
        final var user = (UserDetails) authentication.getPrincipal();
        final var now = new Date();
        final var expirationDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(tokenSecret, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        final var claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException  | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new AuthenticationException(ex);
        }
    }
}
