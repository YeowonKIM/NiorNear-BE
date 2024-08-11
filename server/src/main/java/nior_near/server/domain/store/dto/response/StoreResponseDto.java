package nior_near.server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import nior_near.server.domain.store.entity.Menu;

import java.util.List;

@Getter
@Builder
public class StoreResponseDto {
    private Long storeId;
    private String profileImage;
    private String name;
    private String storePhone;
    private List<String> images;
    private String title;
    private String introduction;
    private List<String> possibleRegion;
    private Long placeId;
    private String placeName;
    private List<String> auths;
    double temperature;
    private List<MenuItem> menus;

    @Getter
    public static class MenuItem {
        private Long menuId;
        private String menuName;
        private String menuImage;
        private String menuIntroduction;
        private Long menuPrice;
        private Integer menuGram;

        public MenuItem(Menu menu) {
            this.menuId = menu.getId();
            this.menuName = menu.getName();
            this.menuImage = menu.getImageLink();
            this.menuIntroduction = menu.getIntroduction();
            this.menuPrice = menu.getPrice();
            this.menuGram = menu.getOneServing();
        }
    }
}
