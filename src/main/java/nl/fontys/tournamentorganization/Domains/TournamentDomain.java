package nl.fontys.tournamentorganization.Domains;

import java.time.LocalDateTime;

public class TournamentDomain {

    private Long id;
    private String name;
    private UserDomain organiser;
    private Integer maxCapacity;
    private LocalDateTime registrationDeadline;
    private LocalDateTime startDate;
    private Integer roundDurationHours;

    public TournamentDomain(
            Long id,
            String name,
            UserDomain organiser,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours
    ) {
        this.id = id;
        this.name = name;
        this.organiser = organiser;
        this.maxCapacity = maxCapacity;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.roundDurationHours = roundDurationHours;
    }

    public TournamentDomain(
            String name,
            UserDomain organiser,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours
    ) {
        this(
                null,
                name,
                organiser,
                maxCapacity,
                registrationDeadline,
                startDate,
                roundDurationHours
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserDomain getOrganiser() {
        return organiser;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public LocalDateTime getRegistrationDeadline() {
        return registrationDeadline;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public Integer getRoundDurationHours() {
        return roundDurationHours;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganiser(UserDomain organiser) {
        this.organiser = organiser;
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
