package nl.fontys.tournamentorganization.DTOs;

import java.time.LocalDateTime;

public class TournamentDTO {
    public String name;
    public Integer maxCapacity;
    public LocalDateTime registrationDeadline;
    public LocalDateTime startDate;
    public Integer roundDurationHours;
}
