package nior_near.server.domain.store.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity @Getter
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String authName;
}
