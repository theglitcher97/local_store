package com.store.local_store.web.filters;

import com.store.local_store.persistence.repo_impl.IAccountRepository;
import com.store.local_store.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private IAccountRepository iAccountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // validate header existence
        if (Objects.isNull(authHeader) || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // validate token existence
        String bearerToken = authHeader.substring("bearer ".length());
        if (bearerToken.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // validate the token
        Claims claims = this.jwtUtils.validateToken(bearerToken);
        // extract email from claims
        String email = this.jwtUtils.getEmail(claims);
        // query user by email
        UserDetails userDetails = this.iAccountRepository.loadUserByUsername(email);

        // load user in security context so username password auth will work
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
