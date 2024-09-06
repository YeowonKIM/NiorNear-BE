package nior_near.server.domain.store.repository;

import nior_near.server.domain.store.entity.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreImageRepository extends JpaRepository<StoreImage, Long> {
}
