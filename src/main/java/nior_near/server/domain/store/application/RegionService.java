package nior_near.server.domain.store.application;

import nior_near.server.domain.store.entity.Region;

import java.util.List;

public interface RegionService {
    public List<Region> getRegions();

    public Region getRegionById(Long regionId);
}
