package code.alba.pharmapro.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String role=
                authentication.getAuthorities().iterator().next().getAuthority();

        switch (role){
            case "ROLE_SUPER_ADMIN":response.sendRedirect("/superadmin");
            break;

            case "ROLE_ADMIN":response.sendRedirect("/admin");
            break;

            case "ROLE_MANAGER":response.sendRedirect("/manager");
            break;
            default:
                response.sendRedirect("/");
                break;
        }
    }
}
