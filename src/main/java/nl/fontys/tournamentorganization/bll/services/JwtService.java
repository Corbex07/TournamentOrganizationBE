package nl.fontys.tournamentorganization.bll.services;

import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(UserDomain userDomain) {
        Instant now = Instant.now();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userDomain.getId()))
                .issuer("tournament-organization-api")
                .audience(List.of("tournament-api"))
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .build();

        JwsHeader header = JwsHeader.with(
                MacAlgorithm.HS256  // surely this is fine right copium
        ).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claimsSet)).getTokenValue();
    }
}
