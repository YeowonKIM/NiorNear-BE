package nior_near.server.domain.store.entity;

import jakarta.persistence.*;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Menu extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ColumnDefault("기본 이미지 링크")
    private String imageLink;

    @Column(nullable = false)
    private Long price;

    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;
}
