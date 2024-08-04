package nior_near.server.domain.store.entity;

import jakarta.persistence.*;
import nior_near.server.domain.order.entity.PossiblePickUp;
import nior_near.server.global.util.Time;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    private Long upperId;

    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PossiblePickUp> possiblePickUpList = new ArrayList<>();
}
