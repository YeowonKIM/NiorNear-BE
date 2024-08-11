package nior_near.server.domain.store.application;

import nior_near.server.domain.store.dto.response.StoreResponseDto;
import nior_near.server.global.common.BaseResponseDto;

public interface StoreQueryService {

    public BaseResponseDto<StoreResponseDto> getStore(Long storeId);

}
