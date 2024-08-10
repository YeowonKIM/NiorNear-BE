package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.dto.response.StoreResponseDto;
import nior_near.server.domain.store.entity.*;
import nior_near.server.domain.store.exception.handler.StoreHandler;
import nior_near.server.domain.store.repository.StoreRepository;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreQueryServiceImpl implements StoreQueryService {

    private final StoreRepository storeRepository;

    @Override
    public BaseResponseDto<StoreResponseDto> getStore(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreHandler(ResponseCode.STORE_NOT_FOUND));

        List<String> auths = store.getStoreAuthList().stream().map(storeAuth -> storeAuth.getAuth().getAuthName()).toList();
        List<String> images = store.getStoreImageList().stream().map(StoreImage::getImageLink).toList();
        List<String> regions = store.getStoreRegionList().stream().map(storeRegion -> storeRegion.getRegion().getName()).toList();
        List<StoreResponseDto.MenuItem> menus = store.getMenuList().stream().map(StoreResponseDto.MenuItem::new).toList();

        StoreResponseDto storeResponseDto = StoreResponseDto.builder()
                .storeId(storeId)
                .name(store.getMember().getName())
                .storePhone(store.getMember().getPhone())
                .placeId(store.getPlace().getId())
                .title(store.getTitle())
                .introduction(store.getIntroduction())
                .placeName(store.getPlace().getAddress() + " " + store.getPlace().getName())
                .profileImage(store.getMember().getProfileImage())
                .temperature(store.getTemperature())
                .auths(auths)
                .images(images)
                .possibleRegion(regions)
                .menus(menus)
                .build();

        return BaseResponseDto.onSuccess(storeResponseDto, ResponseCode.OK);
    }

}
