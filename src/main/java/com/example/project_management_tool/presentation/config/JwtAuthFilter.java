package com.example.project_management_tool.presentation.config;

import com.example.project_management_tool.application.service.UserDetailsServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtHelper jwtHelper;

    public JwtAuthFilter(UserDetailsServiceImpl userDetailsService, JwtHelper jwtHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String workspaceHeader = request.getHeader("WorkspaceId");
            String token = null;
            String username = null;
            UUID companyId = null;
            UUID workspaceId = null;

            UUID companyIdFromURI = UUID.fromString(request.getRequestURI().split("/")[2]) ;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtHelper.extractUserEmail(token);
                companyId = jwtHelper.extractCompanyId(token);
            }

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            if (!companyIdFromURI.equals(companyId)){{
                filterChain.doFilter(request, response);
                return;
            }}

            if (workspaceHeader != null) {
                workspaceId = UUID.fromString(workspaceHeader);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserAndAuthoritiesByUsername(username, workspaceId);
                if (jwtHelper.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);

        } catch (AccessDeniedException e) {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(e.getMessage());
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

}
