package nior_near.server.domain.letter.entity;

import jakarta.persistence.*;
import lombok.Getter;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.global.util.Time;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
public class Letter extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String senderName;

    @Column
    private String imageLink; // 요리사 -> 사용자

    @Column
    private String text; // 사용자 -> 요리사

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LetterStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;
}
