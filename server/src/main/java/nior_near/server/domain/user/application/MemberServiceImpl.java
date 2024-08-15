package nior_near.server.domain.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.letter.application.LetterService;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.auth.dto.NaverAccessTokenInfoResponseDto;
import nior_near.server.global.auth.jwt.TokenParser;
import nior_near.server.global.common.ResponseCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LetterService letterService;
    private final TokenParser tokenParser;

    @Override
    public MyMemberResponseDto getMyProfile(String memberName) {

        final int MY_PAGE_LETTER_LIMIT = 3;
        final int DEFAULT_PAGE = 0;

        Member member = memberRepository.findByName(memberName)
                .orElseThrow(() -> new MemberExceptionHandler(ResponseCode.MEMBER_NOT_FOUND));

        List<LetterResponseDto> letters = letterService.getAllLetters(DEFAULT_PAGE, MY_PAGE_LETTER_LIMIT, memberName);

        return MyMemberResponseDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .point(member.getPoint())
                .imageUrl(member.getProfileImage())
                .letterResponseDtos(letters)
                .build();
    }

    @Transactional(readOnly = true)
    public Member findMemberByName(String name) {
        return memberRepository.findByName(name).orElse(null);
    }

    public String retrieveName(HttpServletRequest request) {

        String hasPrefixAccessToken = tokenParser.parseBearerToken(request);
        log.info(request.toString());

        String name = null;
        if (hasPrefixAccessToken.startsWith("naver_")) {
            String accessToken = hasPrefixAccessToken.substring(6);
            name = "naver_" + tokenPrefixNaver(accessToken);
        }
        else
            throw new MemberExceptionHandler(ResponseCode.TOKEN_PREFIX_VALUE_EXCEPTION);

        return name;
    }

    private String tokenPrefixNaver(String accessToken) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> naverTokenReq =
                new HttpEntity<>(null,headers);

        ResponseEntity<String> resp = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                naverTokenReq,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        NaverAccessTokenInfoResponseDto accessTokenInfo = null;

        try {
            accessTokenInfo = objectMapper.readValue(resp.getBody(), NaverAccessTokenInfoResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(String.valueOf(accessTokenInfo));
        log.info("Member Id : " + accessTokenInfo.getResponse().getId());

        return accessTokenInfo.getResponse().getId();
    }
}
