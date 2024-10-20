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

        // naver OAuth 처리
        if (request.getClientRegistration().getRegistrationId().equals("naver")) {
            Map<String, String> responseMap = (Map<String, String>) oAuth2User.getAttributes().get("response");

            Member member = memberRepository.findByName(responseMap.get("id"))
                    .orElseGet(() -> {
                        Member newMember = new Member(
                                responseMap.get("id"),
                                responseMap.get("name"),
                                responseMap.get("profile_image"),
                                responseMap.get("email"),
                                responseMap.get("mobile"),
                                "naver"
                        );
                        return memberRepository.save(newMember);
                    });

            return new CustomOAuth2Member(
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    "USER"
            );
        }
        throw new OAuth2AuthenticationException("Unsupported OAuth2 provider");
    }

}