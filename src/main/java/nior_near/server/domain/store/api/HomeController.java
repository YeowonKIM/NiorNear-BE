package nior_near.server.domain.store.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.HomeService;
import nior_near.server.domain.store.dto.response.HomeResponseDto;
import nior_near.server.domain.store.dto.response.StoreSearchResponseDto;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    private final HomeService homeService;
    private final MemberService memberService;

    @GetMapping("")
    public BaseResponseDto<HomeResponseDto> getHome(@AuthenticationPrincipal UserDetails userDetails) {
        Long memberId = null;
        if (userDetails != null) {
            Member member = memberService.findMemberByName(userDetails.getUsername());
            memberId = member.getId();
        }

        return BaseResponseDto.onSuccess(homeService.getHome(memberId), ResponseCode.OK);
    }

    @GetMapping("/search")
    public BaseResponseDto<List<StoreSearchResponseDto>> searchStores(@RequestParam(required = false) String keyword) {
        List<StoreSearchResponseDto> storeSearchResponseList = homeService.searchStores(keyword);

        return BaseResponseDto.onSuccess(storeSearchResponseList, ResponseCode.OK);
    }

}
