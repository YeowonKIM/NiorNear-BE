package nior_near.server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import nior_near.server.domain.store.entity.Menu;

import java.util.List;

@Getter
@Builder
public class StoreResponseDto {
    Long storeId;
    String profileImage;
    String name;
    String storePhone;
    List<String> images;
    String title;
    String introduction;
    List<String> possibleRegion;
    Long placeId;
    String placeName;
    List<String> auths;
    double temperature;
    List<MenuItem> menus;

    @Getter
    public static class MenuItem {
        Long menuId;
        String menuName;
        String menuImage;
        String menuIntroduction;
        Long menuPrice;
        Integer menuGram;

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
