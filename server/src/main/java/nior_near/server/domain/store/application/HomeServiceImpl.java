package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.dto.response.HomeResponseDto;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.store.repository.AuthRepository;
import nior_near.server.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public HomeResponseDto getHome(Long regionId) {
        List<Store> stores;

        // 지역 선택이 없을 경우 모든 스토어를 가져옴
        if (regionId == null) {
            stores = storeRepository.findAll();
            regionId = null;
        } else {
            stores = storeRepository.findByRegion_Id(regionId);
        }

        // Chefs와 Stores 데이터를 구성
        List<HomeResponseDto.ChefDto> chefs = stores.stream()
                .map(store -> HomeResponseDto.ChefDto.builder()
                        .name(store.getName())
                        .profileImage(store.getProfileImage())
                        .build())
                .collect(Collectors.toList());

        List<HomeResponseDto.StoreDto> storeDtos = stores.stream()
                .map(store -> {
                    List<String> tags = store.getStoreAuthList().stream()
                            .map(storeAuth -> storeAuth.getAuth().getAuthName())
                            .collect(Collectors.toList());

//                    int reviewCount = store.getMember().getLetters().size();  // letterList의 길이로 판단
                    int reviewCount = 10; // 매핑관계 확인 후 수정 예정

                    return HomeResponseDto.StoreDto.builder()
                            .storeId(store.getId())
                            .profileImage(store.getProfileImage())
                            .name(store.getName())
                            .tags(tags)
                            .introduction(store.getIntroduction())
                            .temperature(store.getTemperature())
                            .reviewCount(reviewCount)
                            .build();
                })
                .collect(Collectors.toList());

        return HomeResponseDto.builder()
                .region(regionId)
                .chefs(chefs)
                .stores(storeDtos)
                .build();
    }
}
