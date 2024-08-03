package nior_near.server.domain.store.entity;

import jakarta.persistence.*;

@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String authName;
}
