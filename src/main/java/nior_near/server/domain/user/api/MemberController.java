package nior_near.server.domain.user.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.dto.response.MyPaymentSummaryResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     member 정보 얻어가는 방법
     1. UserDetails 가져오기
     2. findMemberByName()을 통해 member 획득
     */
    @GetMapping("/info")
    BaseResponseDto<Member> test(@AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findMemberByName(userDetails.getUsername());

        return BaseResponseDto.onSuccess(member, ResponseCode.OK);
    }

    // @Operation(summary = "마이페이지 기본화면 조회")
    @GetMapping
    BaseResponseDto<MyMemberResponseDto> getMyProfile() {

        // String memberName = memberService.retrieveName(request);
        return BaseResponseDto.onSuccess(memberService.getMyProfile(), ResponseCode.OK);
    }

    @GetMapping("/payments")
    BaseResponseDto<List<MyPaymentSummaryResponseDto>> getMyPaymentSummary() {

        return BaseResponseDto.onSuccess(memberService.getMyPaymentSummary(), ResponseCode.OK);
    }
}
