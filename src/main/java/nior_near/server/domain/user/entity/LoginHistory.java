package nior_near.server.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nior_near.server.global.util.Time;

@Entity
@NoArgsConstructor
public class LoginHistory extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public LoginHistory(Member member) {
        this.member = member;
    }
}
