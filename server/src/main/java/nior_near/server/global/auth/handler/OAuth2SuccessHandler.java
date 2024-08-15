package nior_near.server.global.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nior_near.server.domain.user.entity.CustomOAuth2Member;
import nior_near.server.global.auth.jwt.JwtProvider;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();

        String accessToken = oAuth2Member.getAccessToken();
        String refreshToken = oAuth2Member.getRefreshToken();

        Cookie cookie = new Cookie("access_token", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
//        cookie.setDomain("54.180.155.131:8080");

        response.addCookie(cookie);

        // SameSite 속성 추가
        String cookieHeader = String.format("access_token=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=None",
                accessToken, 60 * 60 * 24 * 7);
        response.setHeader("Set-Cookie", cookieHeader);
    }

}
