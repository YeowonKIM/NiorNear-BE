package nior_near.server.domain.store.application;

import nior_near.server.domain.store.dto.request.ChefRegistrationRequestDto;
import nior_near.server.domain.store.dto.response.ChefRegistrationResponseDto;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.common.BaseResponseDto;

import java.io.IOException;

public interface StoreCommandService {

    BaseResponseDto<ChefRegistrationResponseDto> registerCompanyChef(Long memberId, ChefRegistrationRequestDto chefRegistrationRequestDto) throws IOException;
//    BaseResponseDto<ChefRegistrationResponseDto> registerFreelanceChef(Member member, ChefRegistrationRequestDto chefRegistrationRequestDto) throws IOException;

}
