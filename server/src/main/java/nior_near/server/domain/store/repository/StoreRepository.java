package nior_near.server.domain.store.repository;

import nior_near.server.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByRegion_Id(Long regionId);
    List<Store> findAll();
}
