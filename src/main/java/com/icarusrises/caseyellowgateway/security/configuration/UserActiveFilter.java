package com.icarusrises.caseyellowgateway.security.configuration;

import com.icarusrises.caseyellowgateway.domain.users.UserService;
import com.icarusrises.caseyellowgateway.persistence.model.UserDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("UserActiveFilter")
public class UserActiveFilter extends GenericFilterBean {

    private static final String USER_HEADER = "Case-Yellow-User";
    private static final String LOG_URI_PATH = "/central/upload-log-data";
    private static final int INTERNAL_ERROR_CODE = 420;

    private UserService userService;

    @Autowired
    public UserActiveFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String userName = ((HttpServletRequest)request).getHeader(USER_HEADER);

        if (isLogRequest(request) || isUserActive(userName)) {
            filterChain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).setStatus(INTERNAL_ERROR_CODE);
        }
    }

    private boolean isLogRequest(ServletRequest request) {
        String uri = ((HttpServletRequest)request).getRequestURI();

        return uri.equals(LOG_URI_PATH);
    }

    private boolean isUserActive(String userName) {
        if (StringUtils.isEmpty(userName)) {
            return false;
        }

        UserDAO userDAO = userService.getUser(userName);
        return userDAO.isEnabled();
    }
}
