package nior_near.server.domain.store.repository;

import nior_near.server.domain.store.entity.Menu;
import nior_near.server.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore(Store store);
}
