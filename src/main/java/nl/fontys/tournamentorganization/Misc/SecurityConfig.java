
package nl.fontys.tournamentorganization.Misc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver) throws Exception {

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/V1/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex

                        .authenticationEntryPoint(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
                        )

                        .accessDeniedHandler((request, response, exception) ->
                                resolver.resolveException(
                                        request, response, null, exception
                                )
                        )
                );

        return http.build();
    }
}

