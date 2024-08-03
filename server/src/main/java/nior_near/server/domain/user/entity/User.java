package nior_near.server.domain.user.entity;

import jakarta.persistence.*;
import nior_near.server.domain.store.entity.Region;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class User extends Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String social;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 50)
    private String email;

    private String profileImage;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long point;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONMEMBER'")
    private UserAuthorization authorization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region; // 사용자가 선택한 지역
}
