package nior_near.server.domain.store.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.StoreCommandService;
import nior_near.server.domain.store.dto.request.ChefRegistrationRequestDto;
import nior_near.server.domain.store.dto.response.ChefRegistrationResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.common.BaseResponseDto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
@Slf4j
public class StoreController {

    private final StoreCommandService storeCommandService;

    @PostMapping("/near-company")
    public BaseResponseDto<ChefRegistrationResponseDto> createStore(@ModelAttribute ChefRegistrationRequestDto chefRegistrationRequestDto) throws IOException {

        // 추후에 member 토큰에서 받아올 정보
        Long memberId =  1L;

        return storeCommandService.registerCompanyChef(memberId, chefRegistrationRequestDto);

    }


}
