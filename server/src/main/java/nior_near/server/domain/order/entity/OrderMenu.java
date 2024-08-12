package nior_near.server.domain.order.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nior_near.server.domain.store.entity.Menu;

@Entity @Getter
@NoArgsConstructor
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer quantity;

    @Builder
    public OrderMenu(Order order, Menu menu, Integer quantity) {
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
    }
}
