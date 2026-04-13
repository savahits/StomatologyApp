package ru.shmelev.stomatologyapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shmelev.stomatologyapp.service.AdminSetupService;

import java.io.IOException;

@Component
public class SetupFilter extends OncePerRequestFilter {

    private final AdminSetupService adminSetupService;

    public SetupFilter(AdminSetupService adminSetupService) {
        this.adminSetupService = adminSetupService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        boolean adminExists = adminSetupService.isAdminExists();
        String uri = request.getRequestURI();

        if (!adminExists) {
            if (!uri.startsWith("/setup") && !uri.startsWith("/css") && !uri.startsWith("/js")) {
                response.sendRedirect("/setup/admin");
                return;
            }
        } else {
            if (uri.startsWith("/setup")) {
                response.sendRedirect("/login");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}