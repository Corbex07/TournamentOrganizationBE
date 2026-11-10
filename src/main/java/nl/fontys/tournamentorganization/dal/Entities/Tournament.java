package nl.fontys.tournamentorganization.dal.Entities;

import jakarta.persistence.*;
import nl.fontys.tournamentorganization.bll.enums.TournamentStatus;

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

    @Column(name = "description")
    private String description;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "round_duration_hours")
    private Integer roundDurationHours;

    @Column(name = "tournament_status")
    private TournamentStatus tournamentStatus;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;


    public Tournament() {
    }

    public Tournament(
            String name,
            Users organiser,
            String description,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours,
            TournamentStatus tournamentStatus
    ) {
        this.name = name;
        this.organiser = organiser;
        this.maxCapacity = maxCapacity;
        this.description = description;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.roundDurationHours = roundDurationHours;
        this.tournamentStatus = tournamentStatus;
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

    public LocalDateTime getDeletedAt() { return deletedAt; }

    public String getDescription() { return description; }

    public TournamentStatus getTournamentStatus() { return tournamentStatus; }

    public void setId(long id) { this.id = id; }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

