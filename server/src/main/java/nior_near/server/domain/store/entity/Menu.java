package nior_near.server.domain.store.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

@Entity @Getter
@NoArgsConstructor
public class Menu extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ColumnDefault("'기본 이미지 링크'")
    private String imageLink;

    @Column(nullable = false)
    @ColumnDefault("1000")
    private long price = 1000;

    @Column(nullable = false)
    private Integer oneServing; // 1인분 그램수

    private String introduction;

    @Column(nullable = false)
    @ColumnDefault("true")
    private boolean stock = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Builder
    public Menu(String name, String imageLink, Integer oneServing, String introduction, Store store) {
        this.name = name;
        this.imageLink = imageLink;
        this.oneServing = oneServing;
        this.introduction = introduction;
        this.store = store;
    }
}
