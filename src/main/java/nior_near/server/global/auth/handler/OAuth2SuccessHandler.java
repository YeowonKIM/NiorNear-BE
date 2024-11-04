package nior_near.server.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.entity.CustomOAuth2Member;
import nior_near.server.global.auth.jwt.JwtProvider;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();

        Long userId = oAuth2Member.getId();
        String jwtToken = jwtProvider.createToken(userId);

        // 로그인 기록 남기기
        memberService.saveLoginHistory(userId);

        response.setHeader("Authorization", "Bearer " + jwtToken);
        response.sendRedirect("https://www.niornear.store/auth/oauth-response?token=" + jwtToken);
    }

}
