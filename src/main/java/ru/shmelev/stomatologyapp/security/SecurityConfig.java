package ru.shmelev.stomatologyapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/login").permitAll()

                        // read
                        .requestMatchers("/doctors").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/appointments").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/doctors/{id}").hasAnyRole("ADMIN", "DOCTOR")
                        .requestMatchers("/appointments/{id}").hasAnyRole("ADMIN", "DOCTOR")

                        // write
                        .requestMatchers("/doctors/new").hasRole("ADMIN")
                        .requestMatchers("/appointments/new").hasRole("ADMIN")
                        .requestMatchers("/specializations/new").hasRole("ADMIN")
                        .requestMatchers("/doctors", "/doctors/{id}", "/appointments", "/appointments/done", "/specializations").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/doctors", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}