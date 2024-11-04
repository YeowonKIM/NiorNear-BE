package nior_near.server.domain.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.letter.application.LetterService;
import nior_near.server.domain.letter.dto.response.LetterResponseDto;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.repository.OrderRepository;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.store.exception.handler.StoreHandler;
import nior_near.server.domain.store.repository.RegionRepository;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.dto.response.MyPaymentSummaryResponseDto;
import nior_near.server.domain.user.entity.LoginHistory;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.exception.handler.MemberExceptionHandler;
import nior_near.server.domain.user.repository.LoginHistoryRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final LetterService letterService;
    private final RegionRepository regionRepository;
    private final OrderRepository orderRepository;
    private final TokenParser tokenParser;
    private final LoginHistoryRepository loginHistoryRepository;

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

    @Override
    public List<MyPaymentSummaryResponseDto> getMyPaymentSummary(String memberName) {

        Member member = memberRepository.findByName(memberName)
                .orElseThrow(() -> new MemberExceptionHandler(ResponseCode.MEMBER_NOT_FOUND));

        List<Order> orders = orderRepository.findOrderByMemberId(member.getId());

        return orders.stream()
                .map(MyPaymentSummaryResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Member findMemberByName(String name) {
        return memberRepository.findByName(name).orElse(null);
    }

    public void updateMemberRegion(Member member, Long regionId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new StoreHandler(ResponseCode.REGION_NOT_FOUND));

        // 멤버에 region 설정
        member.setRegion(region);

        // 멤버 저장
        memberRepository.save(member);
    }

    public Optional<Member> findMemberById(Long userId) {
        return memberRepository.findById(userId);
    }

    @Override
    public void saveLoginHistory(Member member) {
        LoginHistory loginHistory = LoginHistory.builder().member(member).build();
        loginHistoryRepository.save(loginHistory);
    }

    /**
        [deprecated]
        소셜 로그인이 여러개일때 사용
     */
    public String retrieveName(HttpServletRequest request) {

        String hasPrefixAccessToken = tokenParser.parseBearerToken(request);
        log.info("~~~~~~~~~~~~~~~~~");
        log.info(hasPrefixAccessToken);


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
