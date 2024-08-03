package nior_near.server.domain.order.entity;

import jakarta.persistence.*;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.user.entity.User;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Order extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long totalPrice;

    private String requestMessage;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'CONFIRM'")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;
}
