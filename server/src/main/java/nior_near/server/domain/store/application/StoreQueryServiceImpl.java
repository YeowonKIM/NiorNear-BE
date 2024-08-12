package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.order.entity.Place;
import nior_near.server.domain.store.dto.response.PlaceRegionGetResponse;
import nior_near.server.domain.store.dto.response.StoreResponseDto;
import nior_near.server.domain.store.entity.*;
import nior_near.server.domain.store.exception.handler.StoreHandler;
import nior_near.server.domain.store.repository.PlaceRepository;
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
    private final PlaceRepository placeRepository;

    @Override
    public BaseResponseDto<StoreResponseDto> getStore(Long storeId) {

        Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreHandler(ResponseCode.STORE_NOT_FOUND));

        List<String> auths = store.getStoreAuthList().stream().map(storeAuth -> storeAuth.getAuth().getAuthName()).toList();
        List<String> images = store.getStoreImageList().stream().map(StoreImage::getImageLink).toList();
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
                .possibleRegion(store.getRegion().getName())
                .menus(menus)
                .build();

        return BaseResponseDto.onSuccess(storeResponseDto, ResponseCode.OK);
    }

    @Override
    public BaseResponseDto<PlaceRegionGetResponse> getPlaceRegion(Long placeId) {

        Place place = placeRepository.findById(placeId).orElseThrow(() -> new StoreHandler(ResponseCode.PLACE_NOT_FOUND));

        return BaseResponseDto.onSuccess(PlaceRegionGetResponse.builder().regionId(place.getRegion().getId()).build(), ResponseCode.OK);

    }

}
