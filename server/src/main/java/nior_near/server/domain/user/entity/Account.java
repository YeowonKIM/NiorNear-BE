package nior_near.server.domain.user.entity;

import jakarta.persistence.*;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Account extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String bankName;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
