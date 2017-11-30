package com.icarusrises.caseyellowgateway.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icarusrises.caseyellowgateway.security.model.AccountCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {

        AccountCredentials accountCredentials = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        UsernamePasswordAuthenticationToken authenticationToken = createUsernamePasswordAuthenticationToken(accountCredentials);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        TokenAuthenticationService.addAuthentication(res, auth.getName());
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(AccountCredentials creds) {
        return new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), emptyList());
    }
}
