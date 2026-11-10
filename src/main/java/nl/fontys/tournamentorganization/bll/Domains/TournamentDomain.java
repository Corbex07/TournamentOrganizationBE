package nl.fontys.tournamentorganization.bll.Domains;

import nl.fontys.tournamentorganization.bll.enums.TournamentStatus;

import java.time.LocalDateTime;

public class TournamentDomain {

    private Long id;

    private String name;

    private UserDomain organiser;

    private String description;

    private Integer maxCapacity;

    private LocalDateTime registrationDeadline;

    private LocalDateTime startDate;

    private Integer roundDurationHours;

    private TournamentStatus tournamentStatus;



    public TournamentDomain(
            Long id,
            String name,
            UserDomain organiser,
            String description,
            Integer maxCapacity,
            LocalDateTime registrationDeadline,
            LocalDateTime startDate,
            Integer roundDurationHours,
            TournamentStatus tournamentStatus
    ) {
        this.id = id;
        this.name = name;
        this.organiser = organiser;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.registrationDeadline = registrationDeadline;
        this.startDate = startDate;
        this.roundDurationHours = roundDurationHours;
        this.tournamentStatus = tournamentStatus;
    }

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

    public TournamentDomain() {

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

    public TournamentStatus getTournamentStatus() { return tournamentStatus; }

    public String getDescription() { return description; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganiser(UserDomain organiser) {
        this.organiser = organiser;
    }

    public void setDescription(String description) { this.description = description; }

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

    public void setTournamentStatus(TournamentStatus tournamentStatus) { this.tournamentStatus = tournamentStatus; }
}
