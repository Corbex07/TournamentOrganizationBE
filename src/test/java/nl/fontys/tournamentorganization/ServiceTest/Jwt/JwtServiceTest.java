package nl.fontys.tournamentorganization.ServiceTest.Jwt;

import nl.fontys.tournamentorganization.bll.Domains.UserDomain;
import nl.fontys.tournamentorganization.bll.services.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    private JwtEncoder jwtEncoder;

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateToken_shouldGenerateTokenWithCorrectClaims() {
        // Arrange
        UserDomain user = mock(UserDomain.class);

        when(user.getId()).thenReturn(1L);

        Jwt jwt = Jwt.withTokenValue("test-token")
                .header("alg", "HS256")
                .subject("1")
                .build();

        when(jwtEncoder.encode(any(JwtEncoderParameters.class)))
                .thenReturn(jwt);

        // Act
        String result = jwtService.generateToken(user);

        // Assert
        assertEquals("test-token", result);

        ArgumentCaptor<JwtEncoderParameters> captor =
                ArgumentCaptor.forClass(JwtEncoderParameters.class);

        verify(jwtEncoder).encode(captor.capture());

        JwtEncoderParameters parameters = captor.getValue();

        JwtClaimsSet claims = parameters.getClaims();
        JwsHeader header = parameters.getJwsHeader();

        assertEquals("1", claims.getSubject());
        assertEquals("tournament-organization-api", claims.getClaim("iss"));
        assertEquals(List.of("tournament-api"), claims.getAudience());

        assertNotNull(claims.getIssuedAt());
        assertNotNull(claims.getExpiresAt());

        assertEquals(
                Duration.ofHours(24),
                Duration.between(claims.getIssuedAt(), claims.getExpiresAt())
        );

        assert header != null;
        assertEquals(MacAlgorithm.HS256, header.getAlgorithm());
    }

}
