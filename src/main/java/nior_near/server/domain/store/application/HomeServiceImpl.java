package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.dto.response.HomeResponseDto;
import nior_near.server.domain.store.dto.response.StoreSearchResponseDto;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.store.exception.handler.StoreHandler;
import nior_near.server.domain.store.repository.AuthRepository;
import nior_near.server.domain.store.repository.StoreRepository;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public HomeResponseDto getHome(Long memberId) {
        List<Store> stores = storeRepository.findAll();
        Long regionId = null;

        if (memberId != null) {
            Member member = memberRepository.findById(memberId).get();
            Region region = member.getRegion();

            if (region != null) {
                regionId = region.getId();
                stores = storeRepository.findByRegion_Id(regionId);
            }
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

                    int reviewCount = store.getMember().getReceiverLetterList().size();  // letterList의 길이로 판단

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

    public List<StoreSearchResponseDto> searchStores(String keyword) {
        if (keyword == null)
            keyword = "";

        List<Store> stores = storeRepository.searchStoresByKeyword(keyword);
        List<StoreSearchResponseDto> storeResponseList = stores.stream()
                .map(store -> {
                    List<String> tags = store.getStoreAuthList().stream()
                            .map(auth -> auth.getAuth().getAuthName())
                            .collect(Collectors.toList());

                    int reviewCount = store.getMember().getReceiverLetterList().size();  // letterList의 길이로 판단

                    return StoreSearchResponseDto.builder()
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

        return storeResponseList;
    }
}
