package com.Guzman.UploadFile.web.config;

import com.Guzman.UploadFile.persistence.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.Guzman.UploadFile.domain.service.UserService;
import com.Guzman.UploadFile.persistence.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private UserService userService;
    static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static Algorithm ALGORITHM = Algorithm.HMAC256(key.getEncoded());
    @Autowired
    public JwtUtil(@Lazy UserService userService) {
        this.userService = userService;
    }
    public String create(String email){
        return JWT.create()
                .withSubject(email)
                .withIssuer("TheBbqPlace")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }
    // Método para extraer el email del token
    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }
    // validar el token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    // verificar si  ha expirado
    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
    //obtener Claims del token
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key.getEncoded())
                .parseClaimsJws(token)
                .getBody();
    }
    //cargar el usuario por email
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userService.getprofile(email);

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        return new UserDetailsImpl(user);
    }

}
