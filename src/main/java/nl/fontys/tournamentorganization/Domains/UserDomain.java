package nl.fontys.tournamentorganization.Domains;

public class UserDomain {

    private Long id;
    private String username;
    private String email;
    private String passwordHash;

    public UserDomain(
            Long id,
            String username,
            String email,
            String passwordHash
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public UserDomain(
            String username,
            String email,
            String passwordHash
    ) {
        this(
                null,
                username,
                email,
                passwordHash
        );
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}