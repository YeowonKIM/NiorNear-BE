package nior_near.server.domain.store.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.application.RegionService;
import nior_near.server.domain.store.dto.request.UpdateRegionRequestDto;
import nior_near.server.domain.store.dto.response.RegionsGetResponseDto;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.user.application.MemberService;
import nior_near.server.domain.user.entity.Member;
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
    private final MemberService memberService;

    @GetMapping("")
    public BaseResponseDto<RegionsGetResponseDto> getRegions() {
        List<Region> detailRegions = regionService.getRegions();

        List<RegionsGetResponseDto.Region> detailRegionsDto = detailRegions.stream()
                .map(region -> RegionsGetResponseDto.Region.builder()
                        .id(region.getId())
                        .name(region.getName())
                        .build())
                .collect(Collectors.toList());

        return BaseResponseDto.onSuccess(RegionsGetResponseDto.builder()
                        .detailRegions(detailRegionsDto)
                .build(), ResponseCode.OK);
    }

    @GetMapping("/{regionId}")
    public BaseResponseDto<Region> getRegionById(@PathVariable Long regionId) {
        Region region = regionService.getRegionById(regionId);
        return BaseResponseDto.onSuccess(region, ResponseCode.OK);
    }
}
