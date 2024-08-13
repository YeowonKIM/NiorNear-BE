package nior_near.server.domain.order.repository;

import nior_near.server.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o" +
            " left join fetch o.payment p" +
            " left join fetch o.member m" +
            " where o.id = :orderId")
    Optional<Order> findOrderAndPaymentAndMember(Long orderId);

    @Query("select o from Order o" +
            " left join fetch o.payment p" +
            " where o.id = :orderId")
    Optional<Order> findOrderAndPayment(Long orderId);
}
