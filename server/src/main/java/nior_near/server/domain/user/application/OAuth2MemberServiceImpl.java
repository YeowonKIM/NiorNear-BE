package nior_near.server.domain.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.user.entity.CustomOAuth2Member;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Enumeration;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberServiceImpl extends DefaultOAuth2UserService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String oauthClientName = request.getClientRegistration().getClientName();
        String accessToken = request.getAccessToken().getTokenValue();
        String refreshToken = null;

        log.info("accessToken expiresin : " + request.getAccessToken().getExpiresAt());
        log.info("accessToken issued : " + request.getAccessToken().getIssuedAt());

        Member member = null;

        if (oauthClientName.equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            log.info(responseMap.toString());

            String userId = "naver_" + responseMap.get("id");
            String nickname = responseMap.get("name");
            String profile_img = responseMap.get("profile_image");
            String email = responseMap.get("email");
            String phone = responseMap.get("mobile");

            member = new Member(userId, nickname, profile_img, email, phone, "naver");
        }

        if(memberService.findMemberByName(member.getName()) == null) {
            memberRepository.save(member);
        }

        if (refreshToken == null) return new CustomOAuth2Member(oauthClientName + "_" + accessToken, null);
        else return new CustomOAuth2Member(oauthClientName + "_" + accessToken, oauthClientName + "_" + refreshToken);
    }

}
