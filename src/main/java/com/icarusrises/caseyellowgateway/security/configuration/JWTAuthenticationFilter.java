package com.icarusrises.caseyellowgateway.security.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private static final int INTERNAL_ERROR_CODE = 420;
    public static final String TOKEN_EXPIRED = "tokenExpired";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try {
            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            ((HttpServletResponse)response).addHeader(TOKEN_EXPIRED, "true");
            ((HttpServletResponse)response).setStatus(INTERNAL_ERROR_CODE);
        }

        filterChain.doFilter(request,response);
    }
}
