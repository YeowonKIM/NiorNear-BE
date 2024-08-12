package nior_near.server.domain.store.application;

import nior_near.server.domain.store.dto.response.HomeResponseDto;

public interface HomeService {
    public HomeResponseDto getHome(Long regionId);
}
