package nior_near.server.domain.store.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.StoreCommandService;
import nior_near.server.domain.store.application.StoreQueryService;
import nior_near.server.domain.store.dto.request.CompanyChefRegistrationRequestDto;
import nior_near.server.domain.store.dto.request.FreelanceChefRegistrationRequestDto;
import nior_near.server.domain.store.dto.request.MenuAddRequestDto;
import nior_near.server.domain.store.dto.response.ChefRegistrationResponseDto;
import nior_near.server.domain.store.dto.response.MenuAddResponseDto;
import nior_near.server.domain.store.dto.response.PlaceRegionGetResponse;
import nior_near.server.domain.store.dto.response.StoreResponseDto;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Slf4j
public class StoreController {

    private final StoreCommandService storeCommandService;
    private final StoreQueryService storeQueryService;
    private final MemberService memberService;

    @PostMapping("/near-company")
    public BaseResponseDto<ChefRegistrationResponseDto> createCompanyStore(@ModelAttribute CompanyChefRegistrationRequestDto companyChefRegistrationRequestDto,
                                                                           HttpServletRequest request) throws IOException {

        /**
         * TODO: 추후에 accessToken 에서 받아올 정보
         */
        String memberName = memberService.retrieveName(request);

        return storeCommandService.registerCompanyChef(memberName, companyChefRegistrationRequestDto);

    }

    @PostMapping("/freelance")
    public BaseResponseDto<ChefRegistrationResponseDto> createFreelanceStore(@ModelAttribute FreelanceChefRegistrationRequestDto freelanceChefRegistrationRequestDto,
                                                                             HttpServletRequest request) throws IOException {

        /**
         * TODO: 추후에 accessToken 에서 받아올 정보
         */
        String memberName = memberService.retrieveName(request);

        return storeCommandService.registerFreelanceChef(memberName, freelanceChefRegistrationRequestDto);

    }

    @PostMapping("/{storeId}/menu")
    public BaseResponseDto<MenuAddResponseDto> addMenu(@ModelAttribute MenuAddRequestDto menuAddRequestDto, @PathVariable("storeId") Long storeId,
                                                       HttpServletRequest request) throws IOException {

        /**
         * TODO: 추후에 accessToken 에서 받아올 정보
         */
        String memberName = memberService.retrieveName(request);

        return storeCommandService.addMenu(storeId, memberName, menuAddRequestDto);

    }

    @GetMapping("{storeId}")
    public BaseResponseDto<StoreResponseDto> getStore(@PathVariable("storeId") Long storeId) {

        return storeQueryService.getStore(storeId);
    }

    @GetMapping("/near-company/{placeId}")
    public BaseResponseDto<PlaceRegionGetResponse> getPlaceRegion(@PathVariable("placeId") Long placeId) {

        return storeQueryService.getPlaceRegion(placeId);

    }


}
