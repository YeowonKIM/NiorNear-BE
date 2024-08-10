package nior_near.server.domain.letter.entity;

import jakarta.persistence.*;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.util.Time;

@Entity
public class Letter extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String sender;

    @Column
    private String imageLink; // 요리사 -> 사용자

    @Column
    private String text; // 사용자 -> 요리사

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
