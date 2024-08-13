package nior_near.server.domain.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nior_near.server.domain.payment.entity.Payment;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity @Getter
@Table(name = "orders") // 테이블 이름을 "orders"로 변경
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
    private OrderStatus status = OrderStatus.CONFIRM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenuList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    public Order(Long totalPrice, String requestMessage, String phone, Place place, Member member, Store store) {
        this.totalPrice = totalPrice;
        this.requestMessage = requestMessage;
        this.phone = phone;
        this.place = place;
        this.member = member;
        this.store = store;
    }
}
