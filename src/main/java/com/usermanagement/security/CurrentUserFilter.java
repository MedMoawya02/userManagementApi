package com.usermanagement.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CurrentUserFilter implements Filter {
    public static final String USER_ID_HEADER = "X-User-Id";
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        String userIdHeader=httpRequest.getHeader(USER_ID_HEADER);
        try {
            if(userIdHeader!=null&&!userIdHeader.isBlank()){
                try {
                    Long userId=Long.parseLong(userIdHeader);
                    CurrentUserHolder.setCurrentUserId(userId);
                } catch (NumberFormatException e) {
                    CurrentUserHolder.clear();
                }
            }
            chain.doFilter(request,response);
        }finally {
            CurrentUserHolder.clear();
        }
    }
}
