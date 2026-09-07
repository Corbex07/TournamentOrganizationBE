package nl.fontys.tournamentorganization.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    public String name;

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private Users organiser;

    @Column(name = "max_capacity")
    public Integer maxCapacity;
}

