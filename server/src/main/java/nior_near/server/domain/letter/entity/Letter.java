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

    @Column(nullable = false)
    private String imageLink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
}
