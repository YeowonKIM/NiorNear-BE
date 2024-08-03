package nior_near.server.domain.letter.entity;

import jakarta.persistence.*;
import nior_near.server.domain.user.entity.User;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
}
