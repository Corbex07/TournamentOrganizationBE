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

    public Tournament() {
    }

    public Tournament(String name, Users organiser, Integer maxCapacity) {
        this.name = name;
        this.organiser = organiser;
        this.maxCapacity = maxCapacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Users getOrganiser() {
        return organiser;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }
}

