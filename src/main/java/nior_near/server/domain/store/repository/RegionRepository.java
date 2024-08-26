package nior_near.server.domain.store.repository;

import nior_near.server.domain.store.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findByUpperId(Long upperId);
}
