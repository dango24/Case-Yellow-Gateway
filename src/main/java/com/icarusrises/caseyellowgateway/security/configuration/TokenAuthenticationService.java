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

    private static String TOKEN_AUTHENTICATION_KEY;

    static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(10); // 10 days

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static void setTokenAuthenticationServiceKey(String tokenKey) {
        TOKEN_AUTHENTICATION_KEY = tokenKey;
    }

    static void addAuthentication(HttpServletResponse res, String username) {
        String JWT = Jwts.builder()
                         .setSubject(username)
                         .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                         .signWith(SignatureAlgorithm.HS512, TOKEN_AUTHENTICATION_KEY)
                         .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (nonNull(token)) {

//            Date date = Jwts.parser().setSigningKey(TOKEN_AUTHENTICATION_KEY).parseClaimsJws(token).getBody().getExpiration();
//            System.out.println(TimeUnit.MILLISECONDS.toMinutes(date.getTime() - System.currentTimeMillis()));
            return getAuthenticationToken(token);
        }

        return null;
    }

    // parse the token.
    private static Authentication getAuthenticationToken(String token) {
        String user = Jwts.parser()
                          .setSigningKey(TOKEN_AUTHENTICATION_KEY)
                          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                          .getBody()
                          .getSubject();

        return nonNull(user) ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
    }
}
