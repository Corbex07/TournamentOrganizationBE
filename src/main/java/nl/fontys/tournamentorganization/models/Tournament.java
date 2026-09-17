package nl.fontys.tournamentorganization.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "round_duration_hours")
    private Integer roundDurationHours;


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

    public void setOrganiser(Users organiser) {
        this.organiser = organiser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setRegistrationDeadline(LocalDateTime registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setRoundDurationHours(Integer roundDurationHours) {
        this.roundDurationHours = roundDurationHours;
    }

}

