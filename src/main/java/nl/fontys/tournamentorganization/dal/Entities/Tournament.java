package nl.fontys.tournamentorganization.dal.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "round_duration_hours")
    private Integer roundDurationHours;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public Tournament() {
    }

    public Tournament(
            String name,
            Users organiser,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours
    ) {
        this.name = name;
        this.organiser = organiser;
        this.maxCapacity = maxCapacity;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.roundDurationHours = roundDurationHours;
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

    public LocalDateTime getRegistrationDeadline() { return registrationDeadline; }

    public LocalDateTime getStartDate() { return startDate; }

    public Integer getRoundDurationHours() { return roundDurationHours; }

    public LocalDateTime getDeletedAt() { return  deletedAt; }

    public void setId(long id) { this.id = id; }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

