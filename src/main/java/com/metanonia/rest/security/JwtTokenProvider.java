package com.metanonia.rest.security;

import com.metanonia.rest.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    @Value("spring.jwt.secret")
    private String secretKey;
    private long tokenValidMilisecond = 1000L * 600;    // 10 min
    @Autowired
    CustomUserDetailService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String uid, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(uid);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getUid(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUid(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            Date a = claimsJws.getBody().getExpiration();
            Date b = new Date();
            return !claimsJws.getBody().getExpiration().before(new Date());
        }
        catch (Exception e) {
            log.info(e.toString());
            return false;
        }
    }
}
