package nior_near.server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HomeResponseDto {
    private Long region;
    private List<ChefDto> chefs;
    private List<StoreDto> stores;

    @Getter
    @Builder
    public static class ChefDto {
        private String profileImage;
        private String name;
    }

    @Getter
    @Builder
    public static class StoreDto {
        private Long storeId;
        private String profileImage;
        private String name;
        private List<String> tags;
        private String title;
        private double temperature;
        private int reviewCount;
    }
}
