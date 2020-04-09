package com.matekeszi.authentication.filter;

import com.matekeszi.authentication.configuration.UserDetailsServiceImpl;
import com.matekeszi.authentication.repository.TokenRepository;
import com.matekeszi.authentication.token.TokenGenerator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthTokenFilter extends GenericFilterBean {

    private TokenRepository tokenRepository;
    private UserDetailsServiceImpl customUserDetailsService;
    private String authTokenHeaderName = "x-auth-token";

    public AuthTokenFilter(UserDetailsServiceImpl userDetailsService, TokenRepository tokenRepository) {
        this.customUserDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String authToken = httpServletRequest.getHeader(authTokenHeaderName);

            if (StringUtils.hasText(authToken)) {
                String username = TokenGenerator.getUserNameFromToken(authToken);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (TokenGenerator.validateToken(authToken, tokenRepository)) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,
                            userDetails.getPassword(), userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}