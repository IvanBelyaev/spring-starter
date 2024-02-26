package org.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.example.spring.database.entity.Role.ADMIN;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/users/registration",
                                "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/users/{id}/delete").hasAuthority(ADMIN.getAuthority())
                        .requestMatchers("/admin/**").hasAuthority(ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
//              .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/users")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login")
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
