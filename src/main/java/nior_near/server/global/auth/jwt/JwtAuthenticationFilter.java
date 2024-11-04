package nior_near.server.global.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.global.common.ResponseCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final TokenParser tokenParser;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenParser.parseBearerToken(request);
            if (token != null && jwtProvider.validateToken(token)) {
                Long userId = jwtProvider.getUserIdFromToken(token);
                Optional<Member> member = memberService.findMemberById(userId);

                if (member != null) {
                    List<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority(member.get().getRole())
                    );

                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    UserDetails userDetails = User.builder()
                            .username(member.get().getName())
                            .password("")
                            .authorities(authorities)
                            .build();

                    AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    securityContext.setAuthentication(authenticationToken);
                    SecurityContextHolder.setContext(securityContext);

                    // 로그인 기록 남기기
                    memberService.saveLoginHistory(member.get());
                }
            }
        } catch (Exception e) {
            logger.error("JWT Authentication failed: ", e);
        }

        filterChain.doFilter(request, response);
    }
}
