package nior_near.server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreSearchResponseDto {
    private Long storeId;
    private String profileImage;
    private String name;
    private List<String> tags;
    private String introduction;
    private double temperature;
    private int reviewCount;
}
