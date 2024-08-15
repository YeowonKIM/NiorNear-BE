package nior_near.server.domain.store.application;

import nior_near.server.domain.store.dto.response.HomeResponseDto;
import nior_near.server.domain.store.dto.response.StoreSearchResponseDto;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.store.entity.Store;

import java.util.List;

public interface HomeService {
    public HomeResponseDto getHome(Region region);
    public List<StoreSearchResponseDto> searchStores(String keyword);
}
