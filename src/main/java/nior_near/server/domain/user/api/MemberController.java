package nior_near.server.domain.user.api;

import lombok.RequiredArgsConstructor;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.dto.response.MyMemberResponseDto;
import nior_near.server.domain.user.dto.response.MyPaymentSummaryResponseDto;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // @Operation(summary = "마이페이지 기본화면 조회")
    @GetMapping
    BaseResponseDto<MyMemberResponseDto> getMyProfile() {

        // String memberName = memberService.retrieveName(request);
        return BaseResponseDto.onSuccess(memberService.getMyProfile(), ResponseCode.OK);
    }

    @GetMapping
    BaseResponseDto<List<MyPaymentSummaryResponseDto>> getMyPaymentSummary() {

        return BaseResponseDto.onSuccess(memberService.getMyPaymentSummary(), ResponseCode.OK);
    }

    /**
        member 정보 얻어가는 방법
        1. HttpServletRequest 가져오기
        2. retrieveName() -> findMemberByName()을 통해 member 획득
    */
//    @GetMapping("/info")
//    public BaseResponseDto<Member> retrieveMember(HttpServletRequest request) {
//        String name = memberService.retrieveName(request);
//        Member member = memberService.findMemberByName(name);
//
//        return BaseResponseDto.onSuccess(member, ResponseCode.OK);
//    }
}
