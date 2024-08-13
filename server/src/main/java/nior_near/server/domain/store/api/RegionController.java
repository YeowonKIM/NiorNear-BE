package nior_near.server.domain.store.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.RegionService;
import nior_near.server.domain.store.dto.response.RegionsGetResponseDto;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    @GetMapping("")
    public BaseResponseDto<RegionsGetResponseDto> getRegions(@RequestParam(required = false) Long upperId) {
        /**
            upperId가 -1이면, 상위 카테고리
            upperId가 0 이상이면, 하위 카테고리
            upperId가 NULL이면, default값 처리
        */
        List<Region> upperRegions = regionService.getRegionsByUpperId(-1L);
        List<Region> detailRegions = regionService.getRegionsByUpperId(upperId);

        List<RegionsGetResponseDto.Region> upperRegionsDto = upperRegions.stream()
                .map(region -> RegionsGetResponseDto.Region.builder()
                        .id(region.getId())
                        .name(region.getName())
                        .build())
                .collect(Collectors.toList());

        List<RegionsGetResponseDto.Region> detailRegionsDto = detailRegions.stream()
                .map(region -> RegionsGetResponseDto.Region.builder()
                        .id(region.getId())
                        .name(region.getName())
                        .build())
                .collect(Collectors.toList());

        return BaseResponseDto.onSuccess(RegionsGetResponseDto.builder()
                        .upperRegions(upperRegionsDto)
                        .detailRegions(detailRegionsDto)
                .build(), ResponseCode.OK);
    }

    @GetMapping("/{regionId}")
    public BaseResponseDto<Region> getRegionById(@PathVariable Long regionId) {
        Region region = regionService.getRegionById(regionId);
        return BaseResponseDto.onSuccess(region, ResponseCode.OK);
    }
}
