package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.store.exception.handler.StoreHandler;
import nior_near.server.domain.store.repository.RegionRepository;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getRegions() {
//        if (upperId == null) {
//            upperId = 1L; // Default upperId = 1 (서울)
//        }
//        return regionRepository.findByUpperId(upperId);
        return regionRepository.findAll();
    }

    public Region getRegionById(Long regionId) {
        Optional<Region> region = regionRepository.findById(regionId);
        return region.orElseThrow(() -> new StoreHandler(ResponseCode.REGION_NOT_FOUND));
    }
}
