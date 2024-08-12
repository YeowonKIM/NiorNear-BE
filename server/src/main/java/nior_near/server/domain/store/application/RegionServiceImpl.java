package nior_near.server.domain.store.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.domain.store.repository.RegionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public List<Region> getRegionsByUpperId(Long upperId) {
        if (upperId == null) {
            upperId = 1L; // Default upperId = 1 (서울)
        }
        return regionRepository.findByUpperId(upperId);
    }
}
