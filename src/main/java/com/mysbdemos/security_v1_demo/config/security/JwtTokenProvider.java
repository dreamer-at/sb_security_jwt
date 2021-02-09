package com.mysbdemos.security_v1_demo.config.security;

import com.mysbdemos.security_v1_demo.service.user.Impl.UserDetailsServiceImpl;
import com.mysbdemos.security_v1_demo.util.exception.UnauthorizedException;
import io.jsonwebtoken.*;
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
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenProvider {

    private final UserDetailsServiceImpl service;

    @Value("${api.security.jwt.secret-key}")
    private String secret;

    @Value("#{new java.lang.Integer('${api.security.jwt.expiration-time}')}")
    private Integer expirationMinutes;

    @Value("${api.security.jwt.secret-key}")
    private String authHeader;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(UTF_8));
    }

    public String createToken(final String username, final String role) {
        var issuedAt = LocalDateTime.now();
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(UTF_8))
                .setClaims(claims)
                .setIssuedAt(Timestamp.valueOf(issuedAt))
                .setExpiration(Timestamp.valueOf(issuedAt.plusMinutes(expirationMinutes)))
                .compact();

    }

    public boolean validateToken(final String token) {
        return isClaimsIsValid(parseClaims(token));
    }

    @Transactional
    public Authentication getAuthentication(final String token) {
        Claims claims = parseClaims(token);
        if (!isClaimsIsValid(claims)) {
            throw new UnauthorizedException("Token parsing exception!");
        }
        String username = claims.getSubject();
        UserDetails userDetails = service.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UnauthorizedException("User is not found or is inactive!");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    private Claims parseClaims(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes(UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            log.warn("Request to parse expired JWT:{} failed:{}", token, ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.warn("Request to parse unsupported JWT:{} failed:{}", token, ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.warn("Request to parse invalid JWT:{} failed:{}", token, ex.getMessage());
        } catch (SignatureException ex) {
            log.warn("Request to parse JWT with invalid signature:{} failed:{}", token, ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.warn("Request to parse empty or nullable JWT:{} failed:{}", token, ex.getMessage());
        }
        return null;
    }

    private boolean isClaimsIsValid(final Claims claims) {
        return claims != null && !claims.getExpiration().before(new Date());
    }

    public String resolveToken(final HttpServletRequest req) {
        return req.getHeader(authHeader);
    }
}
