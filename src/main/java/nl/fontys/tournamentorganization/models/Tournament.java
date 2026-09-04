package nl.fontys.tournamentorganization.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private Users organiser;
    @Column(name = "max_capacity")
    private Integer maxCapacity;
}

