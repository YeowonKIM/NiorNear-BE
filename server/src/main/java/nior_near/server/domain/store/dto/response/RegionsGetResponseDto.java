package nior_near.server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RegionsGetResponseDto {
    private List<Region> upperRegions;
    private List<Region> detailRegions;

    @Getter
    @Builder
    public static class Region {
        private Long id;
        private String name;
    }
}
