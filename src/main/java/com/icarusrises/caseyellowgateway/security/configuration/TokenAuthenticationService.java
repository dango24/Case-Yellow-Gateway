package com.icarusrises.caseyellowgateway.security.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;

public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(10); // 10 days

    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                         .setSubject(username)
                         .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                         .signWith(SignatureAlgorithm.HS512, SECRET)
                         .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                              .setSigningKey(SECRET)
                              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                              .getBody()
                              .getSubject();

            return nonNull(user) ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
        }
        return null;
    }
}
