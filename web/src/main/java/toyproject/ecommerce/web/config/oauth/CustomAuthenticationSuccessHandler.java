package toyproject.ecommerce.web.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import toyproject.ecommerce.core.domain.Member;
import toyproject.ecommerce.web.config.oauth.dto.CustomUserDetails;
import toyproject.ecommerce.web.config.oauth.dto.SessionUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session =  request.getSession();

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        Member member = principal.getMember();
        session.setAttribute("member", new SessionUser(member));

        response.sendRedirect("/");
    }
}
