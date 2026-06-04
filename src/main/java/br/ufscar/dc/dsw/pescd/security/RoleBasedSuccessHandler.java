package br.ufscar.dc.dsw.pescd.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RoleBasedSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = "/painel";

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SECRETARIO"))) {
            targetUrl = "/painel";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROFESSOR"))) {
            targetUrl = "/professor/atuacao";
        }

        response.sendRedirect(targetUrl);
    }
}
