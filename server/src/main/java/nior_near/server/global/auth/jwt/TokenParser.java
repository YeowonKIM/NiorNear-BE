package nior_near.server.global.auth.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class TokenParser {
    public String parseBearerToken(HttpServletRequest request) {
        Optional<String> accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();

        if (accessToken.isPresent()) {
            return accessToken.get();
        } else {
            throw new MemberExceptionHandler(ResponseCode.ACCESS_TOKEN_NOT_FOUND);
        }
    }
}
